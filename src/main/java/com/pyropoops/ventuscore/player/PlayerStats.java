package com.pyropoops.ventuscore.player;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.utils.IPlayerTickUpdater;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
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

public class PlayerStats implements Listener, IPlayerTickUpdater {
    public static PlayerStats instance;

    private PlayerDataHandler dataHandler;

    public PlayerStats() {
        this.dataHandler = VentusCore.instance.playerDataHandler;
        VentusCore.tickUpdaterHandler.register(this);
        PluginHelper.registerListener(this);
    }

    public static void register() {
        if (instance == null)
            instance = new PlayerStats();
    }

    @EventHandler
    public void onBlockMined(BlockBreakEvent e) {
        Player player = e.getPlayer();
        dataHandler.setBlocksBroken(player, dataHandler.getBlocksBroken(player) + 1);
        dataHandler.addExp(player, 10);
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent e) {
        if (e.getEntity().getKiller() != null) {
            Player killer = e.getEntity().getKiller();
            int kills = dataHandler.getKills(killer);
            VentusCore.instance.playerDataHandler.setKills(killer, kills + 1);
            dataHandler.addExp(killer, 100);
        }
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e) {
        dataHandler.setChatMessages(e.getPlayer(), dataHandler.getChatMessages(e.getPlayer()) + 1);
    }

    @Override
    public void onPlayerUpdate(Player player) {
        dataHandler.check(player);
    }

    public void ontimeReward(Player player) {
        player.sendMessage(Methods.color("&aYou played for an hour and received 20 tokens!"));
        dataHandler.setTokens(player, dataHandler.getTokens(player) + 20);
        dataHandler.addExp(player, 1000);
    }
}
