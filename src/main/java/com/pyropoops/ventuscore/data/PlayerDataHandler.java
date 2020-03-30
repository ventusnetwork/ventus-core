package com.pyropoops.ventuscore.data;

import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.data.listeners.DataListener;
import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.ChatColor;
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
        dataObject.saveFile(new JSONObject());

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
        return DataObject.dataObjects.get(playerDataFolderPath + "/" + player.getUniqueId() + ".json");
    }

    @Override
    public int getTokens(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("tokens")).intValue();
        } catch (NumberFormatException | NullPointerException e) {
            this.setTokens(player, ConfigHandler.mainConfig.getConfig().getInt("default-tokens"));
            return getTokens(player);
        }
    }

    @Override
    public int getLevel(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("level")).intValue();
        } catch (NumberFormatException | NullPointerException e) {
            this.setLevel(player, 1);
            return this.getLevel(player);
        }
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
        } catch (NumberFormatException | NullPointerException e) {
            this.setChatMessages(player, 0);
            return this.getChatMessages(player);
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
        } catch (NumberFormatException | NullPointerException e) {
            this.setKills(player, 0);
            return this.getKills(player);
        }
    }

    @Override
    public void setKills(OfflinePlayer player, int kills) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("kills", kills);
        this.getPlayerData(player).saveFile(json);
    }

    @Override
    public double getExp(OfflinePlayer player) {
        try {
            return ((Number) this.getPlayerData(player).getDataObject().get("exp")).doubleValue();
        } catch (NumberFormatException | NullPointerException e) {
            this.setExp(player, 0);
            return this.getExp(player);
        }
    }

    @Override
    public void setExp(OfflinePlayer player, double exp) {
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("exp", exp);
        this.getPlayerData(player).saveFile(json);
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
            }
            this.setExp(player, exp);
        }
    }

    @Override
    public ChatColor getChatColor(OfflinePlayer player) {
        String s = (String) this.getPlayerData(player).getDataObject().get("chat-color");
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
        JSONObject json = this.getPlayerData(player).getDataObject();
        json.put("chat-color", String.valueOf(chatColor.getChar()));
        this.getPlayerData(player).saveFile(json);
    }

}
