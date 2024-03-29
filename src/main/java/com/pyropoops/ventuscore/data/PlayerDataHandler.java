package com.pyropoops.ventuscore.data;

import com.pyropoops.ventuscore.data.listeners.DataListener;
import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.player.PlayerStats;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

public class PlayerDataHandler implements IPlayerDataHandler {
    private String playerDataFolderPath;

    public PlayerDataHandler(String playerDataFolderPath) {
        this.playerDataFolderPath = playerDataFolderPath;
        PluginHelper.registerListener(new DataListener());
    }

    @Override
    public void createPlayerData(OfflinePlayer player, boolean replace) {
        new DataObject(player.getName() + ".yml", this.playerDataFolderPath);
    }

    @Override
    public boolean doesPlayerDataExist(OfflinePlayer player) {
        for (DataObject dataObject : DataObject.dataObjects) {
            if (dataObject.getName().equalsIgnoreCase(player.getName() + ".yml")) return true;
        }
        return false;
    }

    @Override
    public DataObject getPlayerData(OfflinePlayer player) {
        for (DataObject dataObject : DataObject.dataObjects) {
            if (dataObject.getName().equalsIgnoreCase(player.getName() + ".yml")) return dataObject;
        }
        createPlayerData(player, false);
        return getPlayerData(player);
    }

    @Override
    public int getTokens(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("tokens")) {
            return this.getPlayerData(player).getDataObject().getInt("tokens");
        }
        this.setTokens(player, 0);
        return 0;
    }

    @Override
    public int getLevel(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("level")) {
            return this.getPlayerData(player).getDataObject().getInt("level");
        }
        this.setLevel(player, 1);
        return 1;
    }


    @Override
    public void setTokens(OfflinePlayer player, int tokens) {
        this.getPlayerData(player).getDataObject().set("tokens", tokens);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public void setLevel(OfflinePlayer player, int level) {
        this.getPlayerData(player).getDataObject().set("level", level);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public int getChatMessages(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("chat-messages")) {
            return this.getPlayerData(player).getDataObject().getInt("chat-messages");
        }
        this.setChatMessages(player, 0);
        return 0;
    }

    @Override
    public void setChatMessages(OfflinePlayer player, int messages) {
        this.getPlayerData(player).getDataObject().set("chat-messages", messages);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public int getKills(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("kills")) {
            return this.getPlayerData(player).getDataObject().getInt("kills");
        }
        this.setKills(player, 0);
        return 0;
    }

    @Override
    public void setKills(OfflinePlayer player, int kills) {
        this.getPlayerData(player).getDataObject().set("kills", kills);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public double getExp(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("exp")) {
            return this.getPlayerData(player).getDataObject().getInt("exp");
        }
        this.setExp(player, 0);
        return 0;
    }

    @Override
    public void setExp(OfflinePlayer player, double exp) {
        this.getPlayerData(player).getDataObject().set("exp", exp);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public void addExp(OfflinePlayer player, double exp) {
        if (exp >= 0) {
            exp += this.getExp(player);
            int nextLevel = this.getLevel(player) + 1;
            double levelUpExp = (nextLevel * 1000D);
            if (exp >= levelUpExp) {
                exp -= levelUpExp;
                this.setLevel(player, this.getLevel(player) + 1);
                this.setTokens(player, this.getTokens(player) + 20);
                if (player instanceof Player) {
                    ((Player) player).sendMessage(Methods.color("&aYou levelled up! " +
                            "You are now level &2" + this.getLevel(player) + "&a!"));
                    ((Player) player).sendMessage(Methods.color("&aYou gained 20 tokens!"));
                }
            }
            this.setExp(player, exp);
        }
    }

    @Override
    public ChatColor getChatColor(OfflinePlayer player) {
        String s = this.getPlayerData(player).getDataObject().getString("chat-color");
        if (s == null) {
            this.setChatColor(player, ChatColor.WHITE);
            return this.getChatColor(player);
        }
        ChatColor color = ChatColor.getByChar(s);
        if (color == null) {
            this.setChatColor(player, ChatColor.WHITE);
            return this.getChatColor(player);
        }
        return color;
    }

    @Override
    public void setChatColor(OfflinePlayer player, ChatColor chatColor) {
        this.getPlayerData(player).getDataObject().set("chat-color", chatColor.getChar());
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public void setBlocksBroken(OfflinePlayer player, int blocks) {
        this.getPlayerData(player).getDataObject().set("blocks-broken", blocks);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public int getBlocksBroken(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("blocks-broken")) {
            return this.getPlayerData(player).getDataObject().getInt("blocks-broken");
        }
        this.setBlocksBroken(player, 0);
        return 0;
    }

    @Override
    public void setMinutes(OfflinePlayer player, int minutes) {
        this.getPlayerData(player).getDataObject().set("minutes", minutes);
        this.getPlayerData(player).saveConfig();
    }

    @Override
    public int getMinutes(OfflinePlayer player) {
        if (this.getPlayerData(player).getDataObject().contains("minutes")) {
            return this.getPlayerData(player).getDataObject().getInt("minutes");
        }
        this.setMinutes(player, 0);
        return 0;
    }

    public void check(Player player) {
        int ticks = player.getStatistic(Statistic.PLAY_ONE_MINUTE);
        if (ticks % 1200 != 0) return;
        int minutes = ticks / 1200;
        if (minutes != this.getMinutes(player)) {
            this.setMinutes(player, minutes);
            this.addExp(player, 1000D / 30D);
            if (this.getMinutes(player) % 60 == 0) {
                PlayerStats.instance.ontimeReward(player);
            }
        }
    }

}
