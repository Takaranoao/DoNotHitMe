package com.takaranoao.donothitme.fabric.config;

import com.takaranoao.donothitme.fabric.DoNotHitMeFabric;
import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class DNHModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return parent -> DoNotHitMeFabric.getCommonMod().getConfigManager().screenSupplier(parent).get();
    }
}
