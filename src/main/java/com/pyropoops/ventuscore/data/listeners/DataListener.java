package com.pyropoops.ventuscore.data.listeners;

import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class DataListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        VentusCore.instance.playerDataHandler.createPlayerData(e.getPlayer(), false);
    }

}
