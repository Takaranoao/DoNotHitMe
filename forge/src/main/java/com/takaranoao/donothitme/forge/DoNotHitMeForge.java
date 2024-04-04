package com.takaranoao.donothitme.forge;

import com.takaranoao.donothitme.DoNotHitMe;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;

@Mod(DoNotHitMe.MOD_ID)
@Mod.EventBusSubscriber(Dist.CLIENT)
public class DoNotHitMeForge {
    private final DoNotHitMe commonMod;

    public DoNotHitMeForge() {
        this.commonMod = new DoNotHitMe();
        commonMod.init();
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> commonMod.getConfigManager().screenSupplier(parent).get())
        );
    }
}