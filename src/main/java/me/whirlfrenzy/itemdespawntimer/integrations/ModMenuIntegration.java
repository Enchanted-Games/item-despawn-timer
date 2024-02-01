package me.whirlfrenzy.itemdespawntimer.integrations;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;

import me.whirlfrenzy.itemdespawntimer.config.ModConfig;

public class ModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
      return parent -> {
          return ModConfig.createGui(parent);
      };
        
    }
}