package com.takaranoao.donothitme.fabric;

import com.takaranoao.donothitme.DoNotHitMe;
import net.fabricmc.api.ModInitializer;

public class DoNotHitMeFabric implements ModInitializer {
    private static DoNotHitMe commonMod;

    public static DoNotHitMe getCommonMod() {
        return commonMod;
    }

    @Override
    public void onInitialize() {
        commonMod = new DoNotHitMe();
        commonMod.init();
    }
}