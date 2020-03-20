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
        jsonObject.put("kills", 0);
        jsonObject.put("chat-messages", 0);
        // TODO: jsonObject.put("votes", 0);
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
        return dataObject;
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
    public int getTokens(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("tokens")).intValue();
        } catch (NumberFormatException e) {
            return ConfigHandler.mainConfig.getConfig().getInt("default-tokens");
        }
    }

    @Override
    public int getLevel(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("level")).intValue();
        } catch (NumberFormatException e) {
            return 1;
        }
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

    @Override
    public int getChatMessages(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("chat-messages")).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void setChatMessages(OfflinePlayer player, int messages) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("chat-messages", messages);
        this.getPlayerData(player).saveFile(json);
    }

    @Override
    public int getKills(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("kills")).intValue();
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    @Override
    public void setKills(OfflinePlayer player, int kills) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("kills", kills);
        this.getPlayerData(player).saveFile(json);
    }

}
