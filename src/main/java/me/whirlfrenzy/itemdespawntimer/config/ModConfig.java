package me.whirlfrenzy.itemdespawntimer.config;

import dev.isxander.yacl3.api.ConfigCategory;
import dev.isxander.yacl3.api.Option;
import dev.isxander.yacl3.api.OptionDescription;
import dev.isxander.yacl3.api.OptionGroup;
import dev.isxander.yacl3.api.YetAnotherConfigLib;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class ModConfig {
	public void save() { /* save your config! */ }
    
    public static Screen createGui(Screen parent) {
        return YetAnotherConfigLib.createBuilder()
        .title(Text.of("Used for narration. Could be used to render a title in the future."))
        .category(ConfigCategory.createBuilder()
                .name(Text.of("Name of the category"))
                .tooltip(Text.of("This text will appear as a tooltip when you hover or focus the button with Tab. There is no need to add \n to wrap as YACL will do it for you."))
                .group(OptionGroup.createBuilder()
                        .name(Text.of("Name of the group"))
                        .description(OptionDescription.of(Text.of("This text will appear when you hover over the name or focus on the collapse button with Tab.")))
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.of("Boolean Option"))
                                .description(OptionDescription.of(Text.of("This text will appear as a tooltip when you hover over the option.")))
                                .binding(true, () -> ConfigValues.debug, (newVal) -> ConfigValues.debug = newVal)
                                .controller(opt -> BooleanControllerBuilder.create(opt).yesNoFormatter())
                                .build())
                        .build())
                .build())
        .build().generateScreen(parent);
    }
}