package me.whirlfrenzy.itemdespawntimer.mixin;

import me.whirlfrenzy.itemdespawntimer.access.ItemEntityAccessInterface;
import me.whirlfrenzy.itemdespawntimer.config.ConfigValues;
import me.whirlfrenzy.itemdespawntimer.ItemDespawnTimerClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory.Context;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Identifier;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemEntityRenderer.class)
public abstract class ItemEntityRendererMixin extends EntityRenderer<ItemEntity> {
    protected ItemEntityRendererMixin(Context ctx) {
        super(ctx);
    }
    KeyBinding sneakKeyBinding = MinecraftClient.getInstance().options.sneakKey;

    private int getTimerColour(int seconds) {
        if(!ConfigValues.timer_colourBasedOnTimeLeft || seconds > ConfigValues.timer_warningThreshold) {
            return ConfigValues.timer_defaultColour;
        }
        else if(seconds <= ConfigValues.timer_criticalThreshold) {
            return ConfigValues.timer_criticalColour;
        }
        else if(seconds <= ConfigValues.timer_dangerThreshold) {
            return ConfigValues.timer_dangerColour;
        }
        return ConfigValues.timer_warningColour;
    }

    private String getIconSpacing(boolean rightSide) {
        if( (!rightSide && ConfigValues.icon_onRightSide) || (rightSide && !ConfigValues.icon_onRightSide) ) {
            return "";
        }
        return " ";
    }

    @Inject(at = @At(value = "TAIL"), method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void renderTimerText(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if(!((ItemEntityAccessInterface)itemEntity).item_despawn_timer$getTimerLabelVisibility()){
            return;
        }

        // dont render label if more than configured amount of blocks away
        double squaredDistance = this.dispatcher.getSquaredDistanceToCamera(itemEntity);
        if( squaredDistance > ConfigValues.label_renderDistance * ConfigValues.label_renderDistance ) {
            return;
        }

        // dont render label if sneak key isnt pressed and config is onlyVisibleWhenSneaking
        if( ConfigValues.label_onlyVisibleWhenSneaking && !sneakKeyBinding.isPressed() ) {
            return;
        }
        
        int modItemAge = ((ItemEntityAccessInterface)itemEntity).item_despawn_timer$getModItemAge();
        int itemAgeInSeconds = Math.max(0, ((int)Math.ceil(((float) 6000 - (float) modItemAge) / 20)) - 1);

        // dont render label if
        if( itemAgeInSeconds > ConfigValues.timer_maxAmountToShow && ConfigValues.timer_maxAmountToShow > 0) {
            return;
        }

        // clock icon
        Text iconText;
        Style iconStyle = Style.EMPTY
            .withFont(new Identifier("item-despawn-timer", "timer_icon"))
            .withColor(TextColor.fromRgb( ConfigValues.icon_defaultColour ));
        iconText = Text.literal("⌚").setStyle(iconStyle);

        // time until despawn text
        Text ageText;
        Style ageStyle = Style.EMPTY.withColor(TextColor.fromRgb( getTimerColour(itemAgeInSeconds) ));
        if (modItemAge < 0) {
            ageText = Text.literal( getIconSpacing(false) + "∞" + getIconSpacing(true) ).setStyle(ageStyle);
        } else {
            ageText = Text.literal( getIconSpacing(false) + formatTimer(itemAgeInSeconds) + getIconSpacing(true) ).setStyle(ageStyle);
        }

        // rendering setup
        matrixStack.push();
        matrixStack.translate(0.0F, itemEntity.getHeight() + 0.75f, 0.0);
        matrixStack.multiply(((EntityRendererAccessor)this).getDispatcher().getRotation());
        matrixStack.scale(-0.025f, -0.025f, -0.025f);

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        matrix4f.translate(new Vector3f(4.0F, 0.0F, 0.0F));

        TextRenderer textRenderer = ((EntityRendererAccessor)this).getTextRenderer();

        // concatenated text
        OrderedText finalText = 
            ConfigValues.icon_onRightSide ? 
                OrderedText.concat(ageText.asOrderedText(), iconText.asOrderedText()) :
            OrderedText.concat(iconText.asOrderedText(), ageText.asOrderedText());
        int textWidth = textRenderer.getWidth(finalText);
        float halfTextWidth = (float) textWidth / 2;

        // draw background
        float textBackgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int)(textBackgroundOpacity * 255.0f) << 24;

        String backgroundStr = "_".repeat(textWidth);
        Text backgroundText = Text.literal(backgroundStr).setStyle(iconStyle);

        textRenderer.draw(backgroundText, -4 - halfTextWidth, -4f, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, j, i);

        // draw timer text
        textRenderer.draw(finalText, -4 - halfTextWidth, -4f, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);

        if(ConfigValues.debug) {
            textRenderer.draw(Text.literal("item age: "    + modItemAge      ), -20, 12f, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);
            textRenderer.draw(Text.literal("item age (s): "+ itemAgeInSeconds), -20, 20f, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);
        } 

        matrixStack.pop();
    }

    private String formatTimer(int inputSeconds) {
        return formatTimer(inputSeconds, "%s:%s");
    }
    private String formatTimer(int inputSeconds, String formatString) {
        //int hours = (inputSeconds % 86400) / 3600 ;
        int minutes = ((inputSeconds % 86400) % 3600) / 60;
        int seconds = ((inputSeconds % 86400) % 3600) % 60;

        String formatted = String.format(formatString, minutes, seconds <= 9 ? "0" + seconds : seconds);

        return formatted;
    }
}
