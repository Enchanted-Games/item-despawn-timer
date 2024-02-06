package me.whirlfrenzy.itemdespawntimer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import me.whirlfrenzy.itemdespawntimer.config.ConfigFileHandling;
import me.whirlfrenzy.itemdespawntimer.networking.PacketReceiver;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class ItemDespawnTimerClient implements ClientModInitializer {
    public static final String CONFIG_FILE_NAME = "configurable_item_timers.properties";
    public static String MOD_ID = "configurable-item-timer";

    public static final Logger log = LogManager.getLogger(MOD_ID);

    public static final boolean isEntityCullingLoaded = FabricLoader.getInstance().isModLoaded("entityculling");

    @Override
    public void onInitializeClient() {
        if(isEntityCullingLoaded) {
            log.warn("EntityCulling is currently installed along side Configurable Item Timers. Please add minecraft:item to EntityCulling's whitelist to prevent timer desyncs.");
        }
        log.info("entityculling " + isEntityCullingLoaded);
        PacketReceiver.initialize();
        ConfigFileHandling.loadConfig();
        ConfigFileHandling.saveConfig();
    }
}
