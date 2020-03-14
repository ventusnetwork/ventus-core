package com.pyropoops.ventuscore.data;

import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

public interface IPlayerDataHandler {
    void createPlayerData(OfflinePlayer player, boolean replace);

    boolean doesPlayerDataExist(OfflinePlayer player);

    DataObject getPlayerData(OfflinePlayer player);

    void savePlayerData(JSONObject json, OfflinePlayer player);

    String getLastKnownUsername(OfflinePlayer player);

    long getTokens(OfflinePlayer player);

    int getLevel(OfflinePlayer player);

    void setLastKnownUsername(OfflinePlayer player, String username);

    void setTokens(OfflinePlayer player, int tokens);

    void setLevel(OfflinePlayer player, int level);

}
