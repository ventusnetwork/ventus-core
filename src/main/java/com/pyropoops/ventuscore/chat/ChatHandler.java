package com.pyropoops.ventuscore.chat;

import at.pcgamingfreaks.MarriageMaster.Bukkit.API.MarriageMasterPlugin;
import at.pcgamingfreaks.MarriageMaster.Bukkit.API.MarriagePlayer;
import com.earth2me.essentials.Essentials;
import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import net.md_5.bungee.api.chat.*;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatHandler implements Listener {
    private static final Pattern url = Pattern.compile("^(?:(https?)://)?([-\\w_.]{2,}\\.[a-z]{2,4})(/\\S*)?$");
    public static ChatHandler instance = null;
    public static HashMap<UUID, String> rainbowChatters;
    public List<String> swearWords;
    private MarriageMasterPlugin marriageMasterPlugin = null;
    private ArrayList<AsyncPlayerChatEvent> chatEvents;
    private Essentials ess = null;

    public ChatHandler() {
        PluginHelper.registerListener(this);
        this.swearWords = ConfigHandler.mainConfig.getConfig().getStringList("swear-words");

        rainbowChatters = new HashMap<>();

        this.chatEvents = new ArrayList<>();

        this.hookEssentials();
        this.hookMarriageAPI();
    }

    public static void register() {
        if (instance == null)
            instance = new ChatHandler();
    }

    public static BaseComponent[] fromLegacyText(String message, net.md_5.bungee.api.ChatColor defaultColor) {
        ArrayList<BaseComponent> components = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        TextComponent component = new TextComponent();
        Matcher matcher = url.matcher(message);

        for (int i = 0; i < message.length(); ++i) {
            char c = message.charAt(i);
            TextComponent old;
            if (c == 167) {
                ++i;
                if (i >= message.length()) {
                    break;
                }

                c = message.charAt(i);
                if (c >= 'A' && c <= 'Z') {
                    c = (char) (c + 32);
                }

                net.md_5.bungee.api.ChatColor format = net.md_5.bungee.api.ChatColor.getByChar(c);
                if (format != null) {
                    if (builder.length() > 0) {
                        old = component;
                        component = new TextComponent(component);
                        old.setText(builder.toString());
                        builder = new StringBuilder();
                        components.add(old);
                    }

                    switch (format) {
                        case BOLD:
                            component.setBold(true);
                            break;
                        case ITALIC:
                            component.setItalic(true);
                            break;
                        case UNDERLINE:
                            component.setUnderlined(true);
                            break;
                        case STRIKETHROUGH:
                            component.setStrikethrough(true);
                            break;
                        case MAGIC:
                            component.setObfuscated(true);
                            break;
                        case RESET:
                            format = defaultColor;
                        default:
                            component = new TextComponent();
                            component.setColor(format);

                            component.setBold(false);
                            component.setItalic(false);
                            component.setStrikethrough(false);
                            component.setUnderlined(false);
                            component.setObfuscated(false);

                            break;
                    }
                }
            } else {
                int pos = message.indexOf(32, i);
                if (pos == -1) {
                    pos = message.length();
                }

                if (matcher.region(i, pos).find()) {
                    if (builder.length() > 0) {
                        old = component;
                        component = new TextComponent(component);
                        old.setText(builder.toString());
                        builder = new StringBuilder();
                        components.add(old);
                    }

                    old = component;
                    component = new TextComponent(component);
                    String urlString = message.substring(i, pos);
                    component.setText(urlString);
                    component.setClickEvent(new ClickEvent(ClickEvent.Action.OPEN_URL, urlString.startsWith("http") ? urlString : "http://" + urlString));
                    components.add(component);
                    i += pos - i - 1;
                    component = old;
                } else {
                    builder.append(c);
                }
            }
        }

        component.setText(builder.toString());
        components.add(component);
        return components.toArray(new BaseComponent[0]);
    }

    private void hookEssentials() {
        if (VentusCore.instance.getServer().getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials) VentusCore.instance.getServer().getPluginManager().getPlugin("Essentials");
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

    private void hookMarriageAPI() {
        Plugin plugin = VentusCore.instance.getServer().getPluginManager().getPlugin("MarriageMaster");
        if (plugin instanceof MarriageMasterPlugin) {
            this.marriageMasterPlugin = (MarriageMasterPlugin) plugin;
        }
    }

    private OfflinePlayer getPartner(Player player) {
        MarriagePlayer marriagePlayer = this.marriageMasterPlugin.getPlayerData(player);
        MarriagePlayer partner = marriagePlayer.getPartner();
        return partner == null ? null : partner.getPlayer();
    }

    public void onChat(AsyncPlayerChatEvent e) {
        if (!this.isEventCancelled(e)) {
            ComponentBuilder builder = new ComponentBuilder("");

            String playerInfo = this.getStats(e.getPlayer());

            OfflinePlayer partner = getPartner(e.getPlayer());
            if(partner != null) {
                builder.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Methods.color("&cMarried to: &7" +
                        partner.getName())).create())).append(fromLegacyText(Methods.color("&c\u2764"), net.md_5.bungee.api.ChatColor.RED));
                builder.append(fromLegacyText(" ", net.md_5.bungee.api.ChatColor.WHITE));
            }

            builder.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/msg " + e.getPlayer().getName() + " "))
                    .event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Methods.color(playerInfo)).create()))
                    .append(fromLegacyText(e.getPlayer().getDisplayName(), net.md_5.bungee.api.ChatColor.WHITE));

            String message = rainbowChatters.containsKey(e.getPlayer().getUniqueId()) ? rainbow(e.getMessage(), rainbowChatters.get(e.getPlayer().getUniqueId())) : e.getMessage();
            builder.reset().append(fromLegacyText(Methods.color("&7 » &f") + VentusCore.instance.playerDataHandler.getChatColor(e.getPlayer()) + message, net.md_5.bungee.api.ChatColor.WHITE));

            BaseComponent[] baseComponents = builder.create();
            for (Player p : VentusCore.instance.getServer().getOnlinePlayers()) {
                p.spigot().sendMessage(baseComponents);
            }

            VentusCore.instance.getServer().getLogger().info(ChatColor.stripColor(e.getPlayer().getDisplayName() + " » " + e.getMessage()));

            e.setCancelled(true);

            this.chatEvents.clear();
            this.chatEvents.add(e);
        }
    }

    public AsyncPlayerChatEvent onSwear(AsyncPlayerChatEvent e) {
        if (playerBypassChat(e.getPlayer())) {
            return e;
        }
        for (String swear : this.swearWords) {
            if (e.getMessage().toLowerCase().contains(swear.toLowerCase())) {
                StringBuilder censor = new StringBuilder();
                for (int i = 0; i < swear.length(); i++) {
                    censor.append("*");
                }
                e.setMessage(e.getMessage().toLowerCase().replaceAll(swear.toLowerCase(), censor.toString()));
            }
        }
        return e;
    }

    public boolean onCharacterSpam(AsyncPlayerChatEvent e) {
        if (playerBypassChat(e.getPlayer())) {
            return true;
        }
        int count = 0;
        char[] array = e.getMessage().toCharArray();
        for (int i = 1; i < array.length; i++) {
            if (array[i] == array[i - 1]) {
                count++;
            }
        }
        if (count > 5) {
            e.getPlayer().sendMessage(Methods.color("&cPlease do not character spam!"));
            return false;
        }
        return true;
    }

    @EventHandler
    public void onAsyncChat(AsyncPlayerChatEvent e) {
        e = onSwear(e);
        boolean charSpam = onCharacterSpam(e);
        if (charSpam) {
            onChat(e);
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
