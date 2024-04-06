package com.takaranoao.donothitme.mixin;

import com.takaranoao.donothitme.DoNotHitMe;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
@Environment(EnvType.CLIENT)
public class MixinMinecraft {
    @Shadow
    public HitResult hitResult;
    @Nullable
    @Shadow
    public LocalPlayer player;

    @Inject(method = "startUseItem", at = @At("RETURN"))
    private void startUseItem(CallbackInfo ci) {
        var mod = DoNotHitMe.getInstance();
        if (mod == null || player == null) {
            return;
        }
        mod.handleAfterStartUseItem(player);
    }

    @Inject(method = "startAttack", at = @At("HEAD"), cancellable = true)
    private void startAttack(CallbackInfoReturnable<Boolean> cir) {
        var mod = DoNotHitMe.getInstance();
        if (mod == null || player == null) {
            return;
        }
        if (!mod.handleStartAttack(hitResult)) {
            cir.setReturnValue(false);
        }
    }
}
