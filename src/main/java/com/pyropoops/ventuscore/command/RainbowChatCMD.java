package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.chat.ChatHandler;
import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RainbowChatCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!VentusCore.permissionManager.hasPermission(sender, Permissions.RAINBOW_CHAT.value(), true, false))
            return true;
        if (!(sender instanceof Player)) {
            sender.sendMessage(Methods.color("&cYou must be a player for this!"));
            return true;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            if (ChatHandler.rainbowChatters.containsKey(player.getUniqueId())) {
                ChatHandler.rainbowChatters.remove(player.getUniqueId());
                player.sendMessage(Methods.color("&4&lVENTUS &7» &cYour chat is no longer rainbow."));
            } else {
                ChatHandler.rainbowChatters.put(player.getUniqueId(), ConfigHandler.mainConfig.getConfig().getString("rainbow-sequence"));
                player.sendMessage(Methods.color("&4&lVENTUS &7» &aYour chat is now in rainbow."));
            }
        } else {
            ChatHandler.rainbowChatters.put(player.getUniqueId(), args[0]);
            player.sendMessage(Methods.color("&4&lVENTUS &7» &aYour chat is now in rainbow."));
        }
        return true;
    }
}
