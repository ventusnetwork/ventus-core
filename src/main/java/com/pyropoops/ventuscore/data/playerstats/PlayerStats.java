package com.pyropoops.ventuscore.data.playerstats;

import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.event.Listener;

public class PlayerStats implements Listener {
    public static PlayerStats instance;

    public PlayerStats() {
        PluginHelper.registerListener(this);
    }

    public static void register() {
        if (instance == null)
            instance = new PlayerStats();
    }

}
