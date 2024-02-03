package me.whirlfrenzy.itemdespawntimer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.whirlfrenzy.itemdespawntimer.config.ConfigFileHandling;
import me.whirlfrenzy.itemdespawntimer.networking.PacketReceiver;
import net.fabricmc.api.ClientModInitializer;

public class ItemDespawnTimerClient implements ClientModInitializer {
    public static final String CONFIG_FILE_NAME = "configurable_item_timers.properties";
    public static String MOD_ID = "configurable-item-timer";

    public static final Logger log = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        PacketReceiver.initialize();
        ConfigFileHandling.loadConfig();
        ConfigFileHandling.saveConfig();

    }
}
