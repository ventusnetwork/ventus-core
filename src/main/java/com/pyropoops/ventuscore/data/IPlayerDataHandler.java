package com.pyropoops.ventuscore.data;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

public interface IPlayerDataHandler {
    void createPlayerData(OfflinePlayer player, boolean replace);

    boolean doesPlayerDataExist(OfflinePlayer player);

    DataObject getPlayerData(OfflinePlayer player);

    void savePlayerData(JSONObject json, OfflinePlayer player);

    String getLastKnownUsername(OfflinePlayer player);

    int getTokens(OfflinePlayer player);

    int getLevel(OfflinePlayer player);

    void setLastKnownUsername(OfflinePlayer player, String username);

    void setTokens(OfflinePlayer player, int tokens);

    void setLevel(OfflinePlayer player, int level);

    int getChatMessages(OfflinePlayer player);

    void setChatMessages(OfflinePlayer player, int messages);

    int getKills(OfflinePlayer player);

    void setKills(OfflinePlayer player, int kills);

    double getExp(OfflinePlayer player);

    void setExp(OfflinePlayer player, double exp);

    void addExp(OfflinePlayer player, double exp);

    // TODO: votes
}
