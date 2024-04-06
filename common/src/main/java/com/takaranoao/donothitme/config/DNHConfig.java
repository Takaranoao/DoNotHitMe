package com.takaranoao.donothitme.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "donothitme")
public class DNHConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip()
    public boolean enable = false;
    @ConfigEntry.Gui.Tooltip()
    public boolean no_pvp = true;
    @ConfigEntry.Gui.Tooltip()
    public boolean no_pve = true;
    @ConfigEntry.Gui.Tooltip()
    public boolean keep_main_hand_wave = true;
    @ConfigEntry.Gui.Tooltip()
    public boolean open_off_hand_wave = false;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean pat_through = false;
}
