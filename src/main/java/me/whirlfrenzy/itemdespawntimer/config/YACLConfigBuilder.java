package me.whirlfrenzy.itemdespawntimer.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.LabelOption;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.ColorControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerFieldControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.gui.controllers.BooleanController;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.awt.Color;

public class YACLConfigBuilder {
    // https://github.com/isXander/Controlify/blob/update/1.20.3/src/main/java/dev/isxander/controlify/gui/screen/GlobalSettingsScreenFactory.java
    public static Screen buildScreen(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
            .title( Text.translatableWithFallback("configurable_item_timers.config.title", "Configurable Item Timers Config") )
            // main category
            .category( ConfigCategory.createBuilder()
                .name( Text.translatableWithFallback("configurable_item_timers.config.category.options", "options") )
                // general group
                .group( OptionGroup.createBuilder()
                    .name( Text.translatableWithFallback("configurable_item_timers.config.group.general", "general settings") )
                    //.description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.general.desc.", "general settings desc") ) )
                    // label render distance
                    .option( Option.<Integer>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.general.option.label_render_distance", "label render distance") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.general.option.label_render_distance.desc", "distance in blocks, 0 will make labels always render") ) )
                        .binding( DefaultConfigValues.label_renderDistance, () -> ConfigValues.label_renderDistance, (newVal) -> ConfigValues.label_renderDistance = newVal )
                        .controller( opt -> IntegerSliderControllerBuilder.create(opt).range(0, 256).step(1) )
                    .build())
                    // label only visible while sneaking
                    .option( Option.<Boolean>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.general.option.only_visible_while_sneaking", "only_visible_while_sneaking") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.general.option.only_visible_while_sneaking.desc", "") ) )
                        .binding( DefaultConfigValues.label_onlyVisibleWhenSneaking, () -> ConfigValues.label_onlyVisibleWhenSneaking, (newVal) -> ConfigValues.label_onlyVisibleWhenSneaking = newVal )
                        .controller( opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true) )
                    .build())
                .build())
                
                // timer group
                .group( OptionGroup.createBuilder()
                    .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display", "timer display") )

                    // timer max amount
                    .option( Option.<Integer>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.max_amount", "max amount") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.max_amount.desc", "maximum amount the timer will show in seconds. label will not render when time until despawn is above this value. 0 will make timer show any value") ) )
                        .binding( DefaultConfigValues.timer_maxAmountToShow, () -> ConfigValues.timer_maxAmountToShow, (newVal) -> ConfigValues.timer_maxAmountToShow = newVal )
                        .controller( opt -> IntegerFieldControllerBuilder.create(opt).min(0) )
                    .build())
                    // timer default colour
                    .option( Option.<Color>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.timer_default_colour", "default colour") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.timer_default_colour.desc", "colour of the timer") ) )
                        .binding( new Color(DefaultConfigValues.timer_defaultColour), () -> new Color(ConfigValues.timer_defaultColour) , (newVal) -> ConfigValues.timer_defaultColour = colorRGBtoInt(newVal.getRGB()) )
                        .controller( opt -> ColorControllerBuilder.create(opt).allowAlpha(false) )
                    .build())
                    // colour based on time remaining
                    .option( Option.<Boolean>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.colour_based_on_time_left", "colour depends on time remaining") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.colour_based_on_time_left.desc", "controls whether to use warning, danger or critical colours") ) )
                        .binding( DefaultConfigValues.timer_colourBasedOnTimeLeft, () -> ConfigValues.timer_colourBasedOnTimeLeft, (newVal) -> ConfigValues.timer_colourBasedOnTimeLeft = newVal )
                        .controller( opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true) )
                    .build())

                    .option( LabelOption.create( Text.translatableWithFallback( "configurable_item_timers.config.group.timer_display.label.colour_overrides", "the options below only apply when `colour depends on time remaining` is on" ) ) )

                    // timer warning threshold
                    .option( Option.<Integer>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.warning_threshold", "warning threshold") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.warning_threshold.desc", "time in seconds where warning colour is used") ) )
                        .binding( DefaultConfigValues.timer_warningThreshold, () -> ConfigValues.timer_warningThreshold, (newVal) -> ConfigValues.timer_warningThreshold = newVal )
                        .controller( opt -> IntegerFieldControllerBuilder.create(opt).min(0) )
                    .build())
                    // timer warning colour
                    .option( Option.<Color>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.warning_colour", "warning colour") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.warning_colour.desc", "warning colour") ) )
                        .binding( new Color(DefaultConfigValues.timer_warningColour), () -> new Color(ConfigValues.timer_warningColour) , (newVal) -> ConfigValues.timer_warningColour = colorRGBtoInt(newVal.getRGB()) )
                        .controller( opt -> ColorControllerBuilder.create(opt).allowAlpha(false) )
                    .build())

                    // timer danger threshold
                    .option( Option.<Integer>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.danger_threshold", "danger threshold") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.danger_threshold.desc", "time in seconds where danger colour is used") ) )
                        .binding( DefaultConfigValues.timer_dangerThreshold, () -> ConfigValues.timer_dangerThreshold, (newVal) -> ConfigValues.timer_dangerThreshold = newVal )
                        .controller( opt -> IntegerFieldControllerBuilder.create(opt).min(0) )
                    .build())
                    // timer danger colour
                    .option( Option.<Color>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.danger_colour", "danger colour") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.danger_colour.desc", "danger colour") ) )
                        .binding( new Color(DefaultConfigValues.timer_dangerColour), () -> new Color(ConfigValues.timer_dangerColour) , (newVal) -> ConfigValues.timer_dangerColour = colorRGBtoInt(newVal.getRGB()) )
                        .controller( opt -> ColorControllerBuilder.create(opt).allowAlpha(false) )
                    .build())

                    // timer critical threshold
                    .option( Option.<Integer>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.critical_threshold", "critical threshold") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.critical_threshold.desc", "time in seconds where critical colour is used") ) )
                        .binding( DefaultConfigValues.timer_criticalThreshold, () -> ConfigValues.timer_criticalThreshold, (newVal) -> ConfigValues.timer_criticalThreshold = newVal )
                        .controller( opt -> IntegerFieldControllerBuilder.create(opt).min(0) )
                    .build())
                    // timer critical colour
                    .option( Option.<Color>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.critical_colour", "critical colour") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.timer_display.option.critical_colour.desc", "critical colour") ) )
                        .binding( new Color(DefaultConfigValues.timer_criticalColour), () -> new Color(ConfigValues.timer_criticalColour) , (newVal) -> ConfigValues.timer_criticalColour = colorRGBtoInt(newVal.getRGB()) )
                        .controller( opt -> ColorControllerBuilder.create(opt).allowAlpha(false) )
                    .build())
                .build())

                // icon group
                .group( OptionGroup.createBuilder()
                    .name( Text.translatableWithFallback("configurable_item_timers.config.group.icon_display", "icon display") )

                    // icon alignment
                    .option( Option.<Boolean>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.icon_display.option.icon_alignment", "icon alignment") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.icon_display.option.icon_alignment.desc", "") ) )
                        .binding( DefaultConfigValues.icon_onRightSide, () -> ConfigValues.icon_onRightSide, (newVal) -> ConfigValues.icon_onRightSide = newVal )
                        .customController(opt -> new BooleanController(opt, YACLConfigBuilder::formatRightLeftBool, false))
                    .build())
                    // timer warning colour
                    .option( Option.<Color>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.icon_display.option.icon_colour", "icon colour") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.icon_display.option.icon_colour.desc", "icon colour") ) )
                        .binding( new Color(DefaultConfigValues.icon_defaultColour), () -> new Color(ConfigValues.icon_defaultColour) , (newVal) -> ConfigValues.icon_defaultColour = colorRGBtoInt(newVal.getRGB()) )
                        .controller( opt -> ColorControllerBuilder.create(opt).allowAlpha(false) )
                    .build())

                .build())
            .build())
                    
            // fixes category
            .category( ConfigCategory.createBuilder()
                .name( Text.translatableWithFallback("configurable_item_timers.config.category.fixes", "fixes") )
                    // use see though render layer for background
                    .option( Option.<Boolean>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.fixes.option.use_see_through_layer", "use SEE_THROUGH layer") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.fixes.option.use_see_through_layer.desc", "fixes entities and translucent blocks being invisible behind the timer label, however, the timer background will appear through walls") ) )
                        .binding( DefaultConfigValues.fixes_useSeeThroughTextLayer, () -> ConfigValues.fixes_useSeeThroughTextLayer, (newVal) -> ConfigValues.fixes_useSeeThroughTextLayer = newVal )
                        .controller( opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true) )
                    .build())
                    // debug mode
                    .option( Option.<Boolean>createBuilder()
                        .name( Text.translatableWithFallback("configurable_item_timers.config.group.fixes.option.debug", "enable debug mode") )
                        .description( OptionDescription.of( Text.translatableWithFallback("configurable_item_timers.config.group.fixes.option.debug.desc", "shows extra information about ItemEntities") ) )
                        .binding( DefaultConfigValues.debug, () -> ConfigValues.debug, (newVal) -> ConfigValues.debug = newVal )
                        .controller( opt -> BooleanControllerBuilder.create(opt).yesNoFormatter().coloured(true) )
                    .build())
            .build())
            .save(new Runnable() {
                @Override
                public void run() {
                    ConfigFileHandling.saveConfig();
                }
            })
        .build().generateScreen(parent);
    }

    private static Text formatRightLeftBool(Boolean bool) {
        return Text.translatableWithFallback( bool ? "configurable_item_timers.values.right" : "configurable_item_timers.values.left", bool ? "right" : "left" );
    }

    private static int colorRGBtoInt(int intFromColor) {
        return intFromColor - 0xff000000;
    }
}
