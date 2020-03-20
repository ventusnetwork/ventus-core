package com.pyropoops.ventuscore.chat;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.List;

public class ChatHandler implements Listener {
    public static ChatHandler instance;

    public List<String> swearWords;

    public ChatHandler() {
        PluginHelper.registerListener(this);
        this.swearWords = ConfigHandler.mainConfig.getConfig().getStringList("swear-words");
    }

    public static void register() {
        if (instance == null)
            instance = new ChatHandler();
    }

    private String getStats(OfflinePlayer player) {
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        String s = "&3&npyropoops\n";
        float tokens = dataHandler.getTokens(player);
        s += "&bTokens: &b" + tokens + "\n";
        s += "&bLevel: &b" + dataHandler.getLevel(player);
        return s;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        e.setCancelled(true);
        TextComponent baseComponent = new TextComponent();
        TextComponent chatComponent = new TextComponent(e.getPlayer().getDisplayName());
        String playerInfo = this.getStats(e.getPlayer());
        chatComponent.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Methods.colour(playerInfo)).create()));
        chatComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + e.getPlayer().getName() + " "));
        baseComponent.addExtra(chatComponent);
        TextComponent messageComponent = new TextComponent(Methods.colour("&7 » &f") + e.getMessage());
        baseComponent.addExtra(messageComponent);
        for (Player p : VentusCore.instance.getServer().getOnlinePlayers()) {
            p.spigot().sendMessage(baseComponent);
        }
    }

    @EventHandler
    public void onSwear(AsyncPlayerChatEvent e) {
        if (playerBypassChat(e.getPlayer())) {
            return;
        }
        for (String swear : this.swearWords) {
            if (e.getMessage().toLowerCase().contains(swear.toLowerCase())) {
                StringBuilder censor = new StringBuilder();
                for (int i = 0; i < swear.length(); i++) {
                    censor.append("*");
                }
                e.setMessage(e.getMessage().replaceAll(swear, censor.toString()));
            }
        }
    }

    @EventHandler
    public void onCharacterSpam(AsyncPlayerChatEvent e) {
        if (playerBypassChat(e.getPlayer())) {
            return;
        }
        int count = 0;
        char[] array = e.getMessage().toCharArray();
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                count++;
            }
        }
        if (count >= 10) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Methods.colour("&cPlease do not character spam!"));
        }
    }

    private boolean playerBypassChat(Player player) {
        return VentusCore.permissionManager.hasPermission(player, Permissions.CHAT_BYPASS.value(), false, false);
    }

}
