package com.takaranoao.donothitme.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigHolder;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.world.InteractionResult;

import java.util.function.Supplier;

@Environment(EnvType.CLIENT)
public class DNHConfigManager {
    private final ConfigHolder<DNHConfig> configHolder;

    public DNHConfigManager() {
        AutoConfig.register(DNHConfig.class, GsonConfigSerializer::new);
        this.configHolder = AutoConfig.getConfigHolder(DNHConfig.class);
        configHolder.registerSaveListener(this::onSave);
        configHolder.registerLoadListener(this::onLoad);
    }

    private InteractionResult onLoad(ConfigHolder<DNHConfig> dnhConfigConfigHolder, DNHConfig dnhConfig) {

        return InteractionResult.PASS;
    }

    private InteractionResult onSave(ConfigHolder<DNHConfig> dnhConfigConfigHolder, DNHConfig dnhConfig) {
        return InteractionResult.PASS;
    }

    public DNHConfig getConfig() {
        return configHolder.getConfig();
    }

    public Supplier<Screen> screenSupplier(Screen parent) {
        return AutoConfig.getConfigScreen(DNHConfig.class, parent);
    }
}
