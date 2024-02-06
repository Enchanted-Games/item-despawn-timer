package me.whirlfrenzy.itemdespawntimer.config;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import me.whirlfrenzy.itemdespawntimer.libs.nu.studer.OrderedProperties;

import me.whirlfrenzy.itemdespawntimer.ItemDespawnTimerClient;
import me.whirlfrenzy.itemdespawntimer.util.Conversions;
import net.fabricmc.loader.api.FabricLoader;

public class ConfigFileHandling {
    public static final String LABEL_RENDER_DISTANCE = "label__render_distance_in_blocks";
    public static final String LABEL_ONLY_VISIBLE_ON_SNEAK = "label__only_visible_when_sneaking";
    public static final String ICON_COLOUR = "icon__colour";
    public static final String ICON_ALIGNMENT = "icon__alignment";
    public static final String TIMER_MAX_AMOUNT = "timer__max_amount";
    public static final String TIMER_DEFAULT_COLOUR = "timer__default_colour";
    public static final String TIMER_COLOUR_BASED_ON_TIME_LEFT = "timer__colour_based_on_time_remaining";
    public static final String TIMER_WARNING_THRESHOLD = "timer__warning_threshold";
    public static final String TIMER_WARNING_COLOUR = "timer__warning_colour";
    public static final String TIMER_DANGER_THRESHOLD = "timer__danger_threshold";
    public static final String TIMER_DANGER_COLOUR = "timer__danger_colour";
    public static final String TIMER_CRITICAL_THRESHOLD = "timer__critical_threshold";
    public static final String TIMER_CRITICAL_COLOUR = "timer__critical_colour";
    public static final String DEBUG = "debug_mode";
    public static final String TEXT_SEE_THROUGH_LAYER = "fixes__use_see_through_layer";
    public static final String ENTITYCULLING_WARNING = "show_entity_culling_warning";
    
    private static void writeProperties(OrderedProperties p) {
        p.setProperty(LABEL_RENDER_DISTANCE, Integer.toString(ConfigValues.label_renderDistance));
        p.setProperty(LABEL_ONLY_VISIBLE_ON_SNEAK, Boolean.toString(ConfigValues.label_onlyVisibleWhenSneaking));
        p.setProperty(ICON_COLOUR, Conversions.decimalToHexColour(ConfigValues.icon_defaultColour));
        p.setProperty(ICON_ALIGNMENT, Conversions.boolToString(ConfigValues.icon_onRightSide, "right","left"));
        p.setProperty(TIMER_MAX_AMOUNT, Integer.toString(ConfigValues.timer_maxAmountToShow));
        p.setProperty(TIMER_DEFAULT_COLOUR, Conversions.decimalToHexColour(ConfigValues.timer_defaultColour));
        p.setProperty(TIMER_COLOUR_BASED_ON_TIME_LEFT, Boolean.toString(ConfigValues.timer_colourBasedOnTimeLeft));
        p.setProperty(TIMER_WARNING_THRESHOLD, Integer.toString(ConfigValues.timer_warningThreshold));
        p.setProperty(TIMER_WARNING_COLOUR, Conversions.decimalToHexColour(ConfigValues.timer_warningColour));
        p.setProperty(TIMER_DANGER_THRESHOLD, Integer.toString(ConfigValues.timer_dangerThreshold));
        p.setProperty(TIMER_DANGER_COLOUR, Conversions.decimalToHexColour(ConfigValues.timer_dangerColour));
        p.setProperty(TIMER_CRITICAL_THRESHOLD, Integer.toString(ConfigValues.timer_criticalThreshold));
        p.setProperty(TIMER_CRITICAL_COLOUR, Conversions.decimalToHexColour(ConfigValues.timer_criticalColour));
        p.setProperty(DEBUG, Boolean.toString(ConfigValues.debug));
        p.setProperty(TEXT_SEE_THROUGH_LAYER, Boolean.toString(ConfigValues.fixes_useSeeThroughTextLayer));
        p.setProperty(ENTITYCULLING_WARNING, Boolean.toString(ConfigValues.fixes_showEntityCullingWarning));
    }

    private static void setConfigFromProperties(OrderedProperties p) {
        ConfigValues.label_renderDistance = Conversions.defaultedStringToInt(getProperty(p, LABEL_RENDER_DISTANCE), ConfigValues.label_renderDistance);
        ConfigValues.label_onlyVisibleWhenSneaking = Conversions.stringToBool(getProperty(p, LABEL_ONLY_VISIBLE_ON_SNEAK));
        try {
            ConfigValues.icon_defaultColour = Conversions.hexColourToDecimal(getProperty(p, ICON_COLOUR));
        } catch (Exception e) {
            ItemDespawnTimerClient.log.warn("Invalid colour \"" + getProperty(p, ICON_COLOUR) + "\" for config option \"" + ICON_COLOUR + "\"");
        }
        ConfigValues.icon_onRightSide = Conversions.stringToBool(getProperty(p, ICON_ALIGNMENT), "right", "left", false);
        ConfigValues.timer_maxAmountToShow = Conversions.defaultedStringToInt(getProperty(p, TIMER_MAX_AMOUNT), ConfigValues.timer_maxAmountToShow);
        try {
            ConfigValues.timer_defaultColour = Conversions.hexColourToDecimal(getProperty(p, TIMER_DEFAULT_COLOUR));
        } catch (Exception e) {
            ItemDespawnTimerClient.log.warn("Invalid colour \"" + getProperty(p, TIMER_DEFAULT_COLOUR) + "\" for config option \"" + TIMER_DEFAULT_COLOUR + "\"");
        }
        ConfigValues.timer_colourBasedOnTimeLeft = Conversions.stringToBool(getProperty(p, TIMER_COLOUR_BASED_ON_TIME_LEFT));
        ConfigValues.timer_warningThreshold = Conversions.defaultedStringToInt(getProperty(p, TIMER_WARNING_THRESHOLD), ConfigValues.timer_warningThreshold);
        try {
            ConfigValues.timer_warningColour = Conversions.hexColourToDecimal(getProperty(p, TIMER_WARNING_COLOUR));
        } catch (Exception e) {
            ItemDespawnTimerClient.log.warn("Invalid colour \"" + getProperty(p, TIMER_WARNING_COLOUR) + "\" for config option \"" + TIMER_WARNING_COLOUR + "\"");
        }
        ConfigValues.timer_dangerThreshold = Conversions.defaultedStringToInt(getProperty(p, TIMER_DANGER_THRESHOLD), ConfigValues.timer_dangerThreshold);
        try {
            ConfigValues.timer_dangerColour = Conversions.hexColourToDecimal(getProperty(p, TIMER_DANGER_COLOUR));
        } catch (Exception e) {
            ItemDespawnTimerClient.log.warn("Invalid colour \"" + getProperty(p, TIMER_DANGER_COLOUR) + "\" for config option \"" + TIMER_DANGER_COLOUR + "\"");
        }
        ConfigValues.timer_criticalThreshold = Conversions.defaultedStringToInt(getProperty(p, TIMER_CRITICAL_THRESHOLD), ConfigValues.timer_criticalThreshold);
        try {
            ConfigValues.timer_criticalColour = Conversions.hexColourToDecimal(getProperty(p, TIMER_CRITICAL_COLOUR));
        } catch (Exception e) {
            ItemDespawnTimerClient.log.warn("Invalid colour \"" + getProperty(p, TIMER_CRITICAL_COLOUR) + "\" for config option \"" + TIMER_CRITICAL_COLOUR + "\"");
        }
        ConfigValues.debug = Conversions.stringToBool(getProperty(p, DEBUG));
        ConfigValues.fixes_useSeeThroughTextLayer = Conversions.stringToBool(getProperty(p, TEXT_SEE_THROUGH_LAYER));
        ConfigValues.fixes_showEntityCullingWarning = Conversions.stringToBool(getProperty(p, ENTITYCULLING_WARNING));
    }

    public static void saveConfig() {
        OrderedProperties configProperties = new OrderedProperties();
        Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve(ItemDespawnTimerClient.CONFIG_FILE_NAME);

        writeProperties(configProperties);

        // create file if it doesn't already exist
        if(!Files.exists(configFilePath) ) {
            try {
                Files.createFile(configFilePath);
            } catch ( IOException exception ) {
                ItemDespawnTimerClient.log.error("Could not create config file \"" + ItemDespawnTimerClient.CONFIG_FILE_NAME + "\"");
                exception.printStackTrace();
                return;
            }
        }

        // save properties to file
        try {
            configProperties.store(Files.newOutputStream(configFilePath), " -- Config file for " + ItemDespawnTimerClient.MOD_ID + " -- ");
        } catch ( IOException exception ) {
            ItemDespawnTimerClient.log.error("Could not write to config file \"" + ItemDespawnTimerClient.CONFIG_FILE_NAME + "\"");
            exception.printStackTrace();
        }
    }
    
    public static void loadConfig() {
        OrderedProperties configProperties = new OrderedProperties();
        Path configFilePath = FabricLoader.getInstance().getConfigDir().resolve(ItemDespawnTimerClient.CONFIG_FILE_NAME);

        // save defaults if no config exists
        if( !Files.exists(configFilePath) ) {
            saveConfig();
        }

        // read properties from config file
        try {
            configProperties.load( Files.newInputStream(configFilePath) );
        } catch ( IOException exception ) {
            ItemDespawnTimerClient.log.error("Could not read config file \"" + ItemDespawnTimerClient.CONFIG_FILE_NAME + "\"");
            exception.printStackTrace();
            return;
        }

        setConfigFromProperties(configProperties);
    }
    
    private static String getProperty(OrderedProperties p, String property) {
        String prop = p.getProperty(property);
        return prop == null ? "" : prop;
    }
}
