package com.pyropoops.ventuscore.data;

import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.data.listeners.DataListener;
import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.OfflinePlayer;
import org.json.simple.JSONObject;

import java.io.File;

public class PlayerDataHandler implements IPlayerDataHandler {
    private String playerDataFolderPath;

    public PlayerDataHandler(String playerDataFolderPath) {
        this.playerDataFolderPath = playerDataFolderPath;
        PluginHelper.registerListener(new DataListener());
    }

    @Override
    public void createPlayerData(OfflinePlayer player, boolean replace) {
        File file = new File(this.playerDataFolderPath + "/" + player.getUniqueId() + ".json");
        if (file.exists() && !replace) {
            return;
        }
        DataObject dataObject = new DataObject(this.playerDataFolderPath + "/" + player.getUniqueId() + ".json", replace);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("last-known-username", player.getName());
        jsonObject.put("tokens", ConfigHandler.mainConfig.getConfig().getInt("default-tokens"));
        jsonObject.put("level", 1);
        dataObject.saveFile(jsonObject);

        DataObject.dataObjects.put(this.playerDataFolderPath + "/" + player.getUniqueId() + ".json", dataObject);
    }

    @Override
    public boolean doesPlayerDataExist(OfflinePlayer player) {
        String path = playerDataFolderPath + "/" + player.getUniqueId() + ".json";
        File file = new File(path);
        if (file.exists() && !DataObject.dataObjects.containsKey(path)) {
            DataObject.dataObjects.put(path, new DataObject(path, false));
            return true;
        }
        return file.exists();
    }

    @Override
    public DataObject getPlayerData(OfflinePlayer player) {
        if (!this.doesPlayerDataExist(player)) {
            this.createPlayerData(player, false);
            return this.getPlayerData(player);
        }
        DataObject dataObject = DataObject.dataObjects.get(playerDataFolderPath + "/" + player.getUniqueId() + ".json");
        if (dataObject != null) {
            return dataObject;
        }
        return null;
    }

    @Override
    public void savePlayerData(JSONObject json, OfflinePlayer player) {
        this.getPlayerData(player).saveFile(json);
    }

    @Override
    public String getLastKnownUsername(OfflinePlayer player) {
        return (String) this.getPlayerData(player).getDataObject().get("last-known-username");
    }

    @Override
    public long getTokens(OfflinePlayer player) {
        return (long) this.getPlayerData(player).getDataObject().get("tokens");
    }

    @Override
    public int getLevel(OfflinePlayer player) {
        return (int) this.getPlayerData(player).getDataObject().get("level");
    }


    @Override
    public void setLastKnownUsername(OfflinePlayer player, String username) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("last-known-username", username);
        this.getPlayerData(player).saveFile(json);
    }

    @Override
    public void setTokens(OfflinePlayer player, int tokens) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("tokens", tokens);
        this.getPlayerData(player).saveFile(json);
    }

    @Override
    public void setLevel(OfflinePlayer player, int level) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("level", level);
        this.getPlayerData(player).saveFile(json);
    }

    // TODO: Get player data
    // TODO: Get this value, get that value [EXAMPLE: PlayerDataHandler#getPlayerTokens() method]

}
