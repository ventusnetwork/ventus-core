package com.pyropoops.ventuscore.player;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;

/**
 * Stats:
 * - Kills
 * - Chat messages
 * - Ontime
 * - Level & tokens += ontime
 * - Level & tokens += kills
 */

// TODO: Add player set stats functionality
public class PlayerStats implements Listener {
    public static PlayerStats instance;

    public PlayerStats() {
        PluginHelper.registerListener(this);
    }

    public static void register() {
        if (instance == null)
            instance = new PlayerStats();
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
            int kills = dataHandler.getKills(killer);
            VentusCore.instance.playerDataHandler.setKills(killer, kills + 1);
            dataHandler.addExp(killer, 100);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        dataHandler.setChatMessages(e.getPlayer(), dataHandler.getChatMessages(e.getPlayer()) + 1);
    }

}
