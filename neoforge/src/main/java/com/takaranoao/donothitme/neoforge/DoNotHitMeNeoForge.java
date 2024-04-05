package com.takaranoao.donothitme.neoforge;

import com.takaranoao.donothitme.DoNotHitMe;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.ConfigScreenHandler;

@Mod(DoNotHitMe.MOD_ID)
public class DoNotHitMeNeoForge {
    private final DoNotHitMe commonMod;

    public DoNotHitMeNeoForge() {
        this.commonMod = new DoNotHitMe();
        commonMod.init();
        ModLoadingContext.get().registerExtensionPoint(
                ConfigScreenHandler.ConfigScreenFactory.class,
                () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> commonMod.getConfigManager().screenSupplier(parent).get())
        );
    }
}