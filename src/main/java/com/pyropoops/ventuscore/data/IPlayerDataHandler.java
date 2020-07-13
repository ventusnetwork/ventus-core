package com.pyropoops.ventuscore.data;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;

public interface IPlayerDataHandler {
    void createPlayerData(OfflinePlayer player, boolean replace);

    boolean doesPlayerDataExist(OfflinePlayer player);

    DataObject getPlayerData(OfflinePlayer player);

    int getTokens(OfflinePlayer player);

    int getLevel(OfflinePlayer player);

    void setTokens(OfflinePlayer player, int tokens);

    void setLevel(OfflinePlayer player, int level);

    int getChatMessages(OfflinePlayer player);

    void setChatMessages(OfflinePlayer player, int messages);

    int getKills(OfflinePlayer player);

    void setKills(OfflinePlayer player, int kills);

    double getExp(OfflinePlayer player);

    void setExp(OfflinePlayer player, double exp);

    void addExp(OfflinePlayer player, double exp);

    ChatColor getChatColor(OfflinePlayer player);

    void setChatColor(OfflinePlayer player, ChatColor chatColor);

    void setBlocksBroken(OfflinePlayer player, int blocks);

    int getBlocksBroken(OfflinePlayer player);

    void setMinutes(OfflinePlayer player, int minutes);

    int getMinutes(OfflinePlayer player);
}
