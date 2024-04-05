package com.takaranoao.donothitme;

import com.takaranoao.donothitme.config.DNHConfigManager;
import com.takaranoao.donothitme.data.DNHKeyType;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import org.jetbrains.annotations.NotNull;

public class DoNotHitMe {
    public static final String MOD_ID = "donothitme";
    private DNHConfigManager configManager;
    private static DoNotHitMe instance;
    private DNHKeyManager keyBindManager;

    public static DoNotHitMe getInstance() {
        return instance;
    }

    public void init() {
        this.configManager = new DNHConfigManager();
        this.keyBindManager = new DNHKeyManager(this);
        instance = this;
        ClientTickEvent.CLIENT_LEVEL_POST.register(this::onTick);
        keyBindManager.getKeyBindings().forEach(KeyMappingRegistry::register);
    }

    private void onTick(ClientLevel clientLevel) {
        if (keyBindManager != null) {
            keyBindManager.onTick();
        }
    }

    public DNHConfigManager getConfigManager() {
        return configManager;
    }

    public void handleKeyPress(@NotNull DNHKeyType keyType) {
        var player = Minecraft.getInstance().player;
        switch (keyType) {
            case SWING_MAIN_HAND -> {
                if (player == null) {
                    return;
                }
                player.swing(InteractionHand.MAIN_HAND);
            }
            case SWING_OFF_HAND -> {
                if (player == null) {
                    return;
                }
                player.swing(InteractionHand.OFF_HAND);
            }
            case ENABLE -> {
                var config = configManager.getConfig();
                config.enable = !config.enable;
                configManager.getConfigHolder().setConfig(config);
                configManager.getConfigHolder().save();
                if (player == null) {
                    return;
                }
                if (config.enable) {
                    player.sendSystemMessage(Component.translatable("message.donothitme.enable"));
                } else {
                    player.sendSystemMessage(Component.translatable("message.donothitme.disable"));
                }
            }
        }
    }
}
