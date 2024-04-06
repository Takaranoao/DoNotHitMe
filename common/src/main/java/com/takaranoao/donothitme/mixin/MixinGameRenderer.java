package com.takaranoao.donothitme.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(GameRenderer.class)
@Environment(EnvType.CLIENT)
public class MixinGameRenderer {
//    @Shadow
//    @Final
//    Minecraft minecraft;
//    @Inject(method = "pick", at = @At("RETURN"))
//    private void pick(float tickDelta, CallbackInfo ci) {
//        var mod = DoNotHitMe.getInstance();
//        if(mod == null || minecraft == null){
//            return;
//        }
//        mod.handleAfterGameRendererPick(minecraft, tickDelta);
//    }
}
