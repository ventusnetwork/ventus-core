package com.pyropoops.ventuscore.permission;


import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class PermissionManager {
    private String noPermissionMessage;

    public PermissionManager(String noPermissionMessage) {
        if (noPermissionMessage == null) {
            noPermissionMessage = ChatColor.RED + "I'm sorry, but you do not have permission to perform this command." +
                    " Please contact the server administrators if you believe that this is in error.";
        }
        this.noPermissionMessage = Methods.color(noPermissionMessage);
    }
    // The message sent to players that lack a certain permission
    public boolean hasPermission(CommandSender sender, String requiredPermissionNode, boolean sendMessage, boolean sendNode) {
        if(sender.isOp()) return true;
        if (!sender.hasPermission(requiredPermissionNode)) {
            if (sendMessage) {
                String message = noPermissionMessage;
                if (sendNode) {
                    message += Methods.color(" &cYou need the permission node &4" + requiredPermissionNode.toLowerCase() + "&c!");
                }
                sender.sendMessage(message);
            }
            return false;
        }
        return true;
    }

}