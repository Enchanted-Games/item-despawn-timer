package me.whirlfrenzy.itemdespawntimer.config;

public class ConfigValues {
	// Live config values
	// These variables are changed when a config file is loaded / when config is edited via YACL.
	// Read from here to get the current user config values
	public static int label_renderDistance = DefaultConfigValues.label_renderDistance;
	public static boolean label_onlyVisibleWhenSneaking = DefaultConfigValues.label_onlyVisibleWhenSneaking;

	public static int icon_defaultColour = DefaultConfigValues.icon_defaultColour;
	public static boolean icon_onRightSide = DefaultConfigValues.icon_onRightSide;

	public static int timer_maxAmountToShow = DefaultConfigValues.timer_maxAmountToShow;
	public static int timer_defaultColour = DefaultConfigValues.timer_defaultColour;
	public static boolean timer_colourBasedOnTimeLeft = DefaultConfigValues.timer_colourBasedOnTimeLeft;
	public static int timer_warningThreshold = DefaultConfigValues.timer_warningThreshold;
	public static int timer_warningColour = DefaultConfigValues.timer_warningColour;
	public static int timer_dangerThreshold = DefaultConfigValues.timer_dangerThreshold;
	public static int timer_dangerColour = DefaultConfigValues.timer_dangerColour;
	public static int timer_criticalThreshold = DefaultConfigValues.timer_criticalThreshold;
	public static int timer_criticalColour = DefaultConfigValues.timer_criticalColour;

  public static boolean debug = DefaultConfigValues.debug;
	public static boolean fixes_useSeeThroughTextLayer = DefaultConfigValues.fixes_useSeeThroughTextLayer;
	public static boolean fixes_showEntityCullingWarning = DefaultConfigValues.fixes_showEntityCullingWarning;
}
