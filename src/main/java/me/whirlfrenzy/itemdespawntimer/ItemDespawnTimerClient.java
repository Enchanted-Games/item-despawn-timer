package me.whirlfrenzy.itemdespawntimer;

import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import me.whirlfrenzy.itemdespawntimer.config.ModConfig;
import me.whirlfrenzy.itemdespawntimer.networking.PacketReceiver;
import net.fabricmc.api.ClientModInitializer;

public class ItemDespawnTimerClient implements ClientModInitializer {
    public static String MOD_ID = "item-despawn-timer";

    @Override
    public void onInitializeClient() {
        PacketReceiver.initialize();

    }
}
