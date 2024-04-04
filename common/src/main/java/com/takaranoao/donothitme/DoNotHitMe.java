package com.takaranoao.donothitme;

import com.takaranoao.donothitme.config.DNHConfigManager;

public class DoNotHitMe {
    public static final String MOD_ID = "donothitme";
    private DNHConfigManager configManager;

    public void init() {
        this.configManager = new DNHConfigManager();
    }

    public DNHConfigManager getConfigManager() {
        return configManager;
    }
}
