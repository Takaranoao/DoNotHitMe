package com.takaranoao.donothitme.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "do_not_hit_me")
public class DNHConfig implements ConfigData {
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean enable = false;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean no_pvp = true;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean no_pve = true;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean keep_main_hand_waving = true;
    @ConfigEntry.Gui.Tooltip(count = 2)
    public boolean open_off_hand_waving = false;
}
