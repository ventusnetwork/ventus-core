package com.pyropoops.ventuscore.chat;

import com.earth2me.essentials.Essentials;
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
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

public class ChatHandler implements Listener {
    public static ChatHandler instance = null;
    public List<String> swearWords;

    private Essentials ess = null;

    public ChatHandler() {
        PluginHelper.registerListener(this);
        this.swearWords = ConfigHandler.mainConfig.getConfig().getStringList("swear-words");

        this.hookEssentials();
    }

    public static void register() {
        if (instance == null)
            instance = new ChatHandler();
    }

    private void hookEssentials() {
        if (VentusCore.instance.getServer().getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials) VentusCore.instance.getServer().getPluginManager().getPlugin("Essentials");
            VentusCore.instance.getLogger().info("Found EssentialsX Economy...");
        }
    }

    private String getStats(Player player) {
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        String s = "&3&n" + player.getName() + "\n";
        if (this.ess != null) {
            BigDecimal money = ess.getUser(player).getMoney();
            if (money.doubleValue() > 0) {
                DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
                s += "&bBalance: $" + decimalFormat.format(money) + "\n";
            } else {
                s += "&bBalance: $0\n";
            }
        }
        s += "&bTokens: " + dataHandler.getTokens(player) + "\n";
        s += "&bLevel: " + dataHandler.getLevel(player) + "\n";
        s += "&bKills: " + dataHandler.getKills(player);
        return s;
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (e.isCancelled()) return;
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

        VentusCore.instance.getServer().getLogger().info(ChatColor.stripColor(e.getPlayer().getDisplayName() + " » " + e.getMessage()));
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
