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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ChatHandler implements Listener {
    public static ChatHandler instance = null;
    public List<String> swearWords;

    public static HashMap<UUID, String> rainbowChatters;

    private ArrayList<AsyncPlayerChatEvent> chatEvents;

    private Essentials ess = null;

    public ChatHandler() {
        PluginHelper.registerListener(this);
        this.swearWords = ConfigHandler.mainConfig.getConfig().getStringList("swear-words");

        rainbowChatters = new HashMap<>();

        this.chatEvents = new ArrayList<>();

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
                s += "&b» Balance: &7$" + decimalFormat.format(money) + "\n";
            } else {
                s += "&b» Balance: &7$0\n";
            }
        }
        s += "&b» Tokens: &7" + dataHandler.getTokens(player) + "\n";
        s += "&b» Level: &7" + dataHandler.getLevel(player) + "\n";
        s += "&b» Kills: &7" + dataHandler.getKills(player);
        return s;
    }

    private boolean isEventCancelled(AsyncPlayerChatEvent e) {
        return e.isCancelled() && !this.chatEvents.contains(e);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        if (!this.isEventCancelled(e)) {
            ComponentBuilder builder = new ComponentBuilder("");

            String playerInfo = this.getStats(e.getPlayer());

            builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + e.getPlayer().getName() + " "))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Methods.colour(playerInfo)).create())).append(TextComponent.fromLegacyText(e.getPlayer().getDisplayName()));

            String message = rainbowChatters.containsKey(e.getPlayer().getUniqueId()) ? rainbow(e.getMessage(), rainbowChatters.get(e.getPlayer().getUniqueId())) : e.getMessage();

            builder.reset().append(TextComponent.fromLegacyText(Methods.colour("&7 » ")
                    + VentusCore.instance.playerDataHandler.getChatColor(e.getPlayer()) + message));

            for (Player p : VentusCore.instance.getServer().getOnlinePlayers()) {
                p.spigot().sendMessage(builder.create());
            }
            VentusCore.instance.getServer().getLogger().info(ChatColor.stripColor(e.getPlayer().getDisplayName() + " » " + e.getMessage()));
            e.setCancelled(true);
            this.chatEvents.clear();
            this.chatEvents.add(e);
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
        if (count > 5) {
            e.setCancelled(true);
            e.getPlayer().sendMessage(Methods.colour("&cPlease do not character spam!"));
        }
    }

    private boolean playerBypassChat(Player player) {
        return VentusCore.permissionManager.hasPermission(player, Permissions.CHAT_BYPASS.value(), false, false);
    }

    private String rainbow(String s, String sequenceString) {
        s = ChatColor.stripColor(s);
        StringBuilder rainbowChat = new StringBuilder();
        char[] sequence = sequenceString.toCharArray();
        int color = 0;
        ChatColor chatColor;
        for (int i = 0; i < s.length(); i++) {
            if (color >= sequence.length) {
                color = 0;
            }
            chatColor = ChatColor.getByChar(sequence[color]);
            if (chatColor == null) {
                return s;
            }
            color++;
            rainbowChat.append(chatColor).append(s.charAt(i));
        }
        return rainbowChat.toString();
    }

}
