package me.whirlfrenzy.itemdespawntimer.mixin;

import me.whirlfrenzy.itemdespawntimer.access.ItemEntityAccessInterface;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
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
   
    int iconColour = 0x8a8a8a;
    int timerColour = 0xdbdbdb;

    @Inject(at = @At(value = "TAIL"), method = "render(Lnet/minecraft/entity/ItemEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V")
    public void renderTimerText(ItemEntity itemEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if(!((ItemEntityAccessInterface)itemEntity).item_despawn_timer$getTimerLabelVisibility()){
            return;
        }

        double d = this.dispatcher.getSquaredDistanceToCamera(itemEntity);
        if( d> 4*4 ) {
            // dont render timer if more than configured amount of blocks away
            return;
        }

        // The remaining seconds label
        // TODO: Investigate ItemEntity as well as other transparency effects not rendering behind the label
        matrixStack.push();
        matrixStack.translate(0.0F, itemEntity.getHeight() + 0.75f, 0.0);
        matrixStack.multiply(((EntityRendererAccessor)this).getDispatcher().getRotation());
        matrixStack.scale(-0.025f,-0.025f, 0.025f);

        Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
        matrix4f.translate(new Vector3f(4.0F, 0.0F, 0.0F));

        TextRenderer textRenderer = ((EntityRendererAccessor)this).getTextRenderer();

        // clock icon
        Text iconText;
        Style iconStyle = Style.EMPTY;
        iconStyle = iconStyle.withFont(new Identifier("item-despawn-timer", "timer_icon"));
        iconStyle = iconStyle.withColor(TextColor.fromRgb(iconColour));
        iconText = Text.literal("⌚").setStyle(iconStyle);

        // time until despawn text
        int modItemAge = ((ItemEntityAccessInterface)itemEntity).item_despawn_timer$getModItemAge();
        Text ageText;
        Style ageStyle = Style.EMPTY;
        ageStyle = ageStyle.withColor(TextColor.fromRgb(timerColour));
        if(modItemAge != -32768){
            ageText = Text.literal(Math.max(0, ((int)Math.ceil(((float) 6000 - (float) modItemAge) / 20)) - 1) + "s").setStyle(ageStyle);
        } else {
            ageText = Text.literal("∞").setStyle(ageStyle);
        }

        OrderedText finalText = OrderedText.concat(iconText.asOrderedText(), ageText.asOrderedText());

        float negativeHalfOfTextWidth = (float) -3 - textRenderer.getWidth(finalText) / 2;
        float textBackgroundOpacity = MinecraftClient.getInstance().options.getTextBackgroundOpacity(0.25f);
        int j = (int)(textBackgroundOpacity * 255.0f) << 24;

        String str = "";
        for (int i2 = 0; i2 < textRenderer.getWidth(finalText); i2++) {
            str += "_";
        }
        Text bg = Text.literal(str).setStyle(iconStyle);

        textRenderer.draw(bg, negativeHalfOfTextWidth, -4, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.SEE_THROUGH, j, i);
        textRenderer.draw(finalText, negativeHalfOfTextWidth, -4, -1, false, matrix4f, vertexConsumerProvider, TextRenderer.TextLayerType.NORMAL, 0, i);
        matrixStack.pop();
    }
}
