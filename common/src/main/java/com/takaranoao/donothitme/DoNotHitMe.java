package com.takaranoao.donothitme;

import com.takaranoao.donothitme.config.DNHConfig;
import com.takaranoao.donothitme.config.DNHConfigManager;
import com.takaranoao.donothitme.data.DNHKeyType;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientTickEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
        PlayerEvent.ATTACK_ENTITY.register(this::onPlayerAttackEntity);
        keyBindManager.getKeyBindings().forEach(KeyMappingRegistry::register);
    }

    private EventResult onPlayerAttackEntity(Player player, Level level, Entity entity, InteractionHand interactionHand, EntityHitResult entityHitResult) {
        var config = configManager.getConfig();
        if (!config.enable) {
            return EventResult.pass();
        }
        if (entity instanceof Player) {
            if (config.no_pvp) {
                return EventResult.interruptFalse();
            }
        } else if (entity instanceof LivingEntity) {
            if (config.no_pve) {
                return EventResult.interruptFalse();
            }
        }
        return EventResult.pass();
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

    public boolean handleStartAttack(@Nullable HitResult hitResult) {
        var config = configManager.getConfig();
        if (!config.enable) {
            return true;
        }
        if (config.keep_main_hand_wave) {
            return true;
        }
        return isHitResultOk(config, hitResult);
    }

    private boolean isHitResultOk(@NotNull DNHConfig config, @Nullable HitResult hitResult) {
        if (hitResult == null) {
            return true;
        }
        if (hitResult.getType() == HitResult.Type.BLOCK || hitResult.getType() == HitResult.Type.MISS) {
            return true;
        }
        if (hitResult instanceof EntityHitResult) {
            var entity = ((EntityHitResult) hitResult).getEntity();
            if (entity instanceof Player) {
                return !config.no_pvp;
            } else if (entity instanceof LivingEntity) {
                return !config.no_pve;
            }
        }
        return true;
    }


    public void handleAfterStartUseItem(@NotNull LocalPlayer player) {
        var config = configManager.getConfig();
        if (!config.enable) {
            return;
        }
        if (player.isHandsBusy()) {
            return;
        }
        if (config.open_off_hand_wave) {
            player.swing(InteractionHand.OFF_HAND);
        }
    }

    public boolean filterTargetEntity(Entity entity1) {
        var config = configManager.getConfig();
        if (!config.enable) {
            return true;
        }
        if (!config.pat_through) {
            return true;
        }
        if (entity1 instanceof Player) {
            return !config.no_pvp;
        } else if (entity1 instanceof LivingEntity) {
            return !config.no_pve;
        }
        return true;
    }

    //这一部分是MixinGameRenderer使用
//    public void handleAfterGameRendererPick(Minecraft minecraft, float tickDelta) {
//        if (minecraft.hitResult == null) {
//            return;
//        }
//        var config = configManager.getConfig();
//        if (!config.pat_through) {
//            return;
//        }
//        if (!isHitResultOk(config, minecraft.hitResult)) {
//            getBlockHitResult(minecraft, tickDelta).ifPresent(
//                    hitResult -> minecraft.hitResult = hitResult
//            );
//        }
//    }
//
//    public Optional<BlockHitResult> getBlockHitResult(@NotNull Minecraft minecraft, float tickDelta) {
//        var player = minecraft.player;
//        if (player == null) {
//            return Optional.empty();
//        }
//        if (minecraft.gameMode == null) {
//            return Optional.empty();
//        }
//        var pickRange = minecraft.gameMode.getPickRange();
//        return Optional.ofNullable(pickBlock(player, pickRange, tickDelta, false));
//
//    }
//    @SuppressWarnings("resource")
//    public BlockHitResult pickBlock(LocalPlayer localPlayer, double pickRange, float tickDelta, boolean fluid) {
//        Vec3 eyePosition = localPlayer.getEyePosition(tickDelta);
//        Vec3 viewVector = localPlayer.getViewVector(tickDelta);
//        return localPlayer.level().clip(
//                new ClipContext(eyePosition, eyePosition.add(viewVector.scale(pickRange)), ClipContext.Block.OUTLINE, fluid ? net.minecraft.world.level.ClipContext.Fluid.ANY : net.minecraft.world.level.ClipContext.Fluid.NONE, localPlayer)
//        );
//    }

}
