package me.whirlfrenzy.itemdespawntimer.config;

public class ConfigValues {
	// Default config values
	// These variables are changed when a config file is loaded / when config is edited via YACL.
  public static boolean debug = false;

	public static int label_renderDistance = 4;
	public static boolean label_onlyVisibleWhenSneaking = false;

	public static int icon_defaultColour = 0xffffff;
	public static boolean icon_onRightSide = false;

	public static int timer_maxAmountToShow = 0;
	public static int timer_defaultColour = 0xffffff;
	public static boolean timer_colourBasedOnTimeLeft = true;
	public static int timer_warningThreshold = 60;
	public static int timer_warningColour = 0xffed69;
	public static int timer_dangerThreshold = 20;
	public static int timer_dangerColour = 0xffb469;
	public static int timer_criticalThreshold = 10;
	public static int timer_criticalColour = 0xff5454;
}
