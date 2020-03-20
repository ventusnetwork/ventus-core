package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class DiscordCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!VentusCore.permissionManager.hasPermission(sender, Permissions.DISCORD.value(), true, false)) return true;
        sender.sendMessage(Methods.colour("&9You can find our discord at: &l&nhttp://discord.ventusnetwork.net/"));
        return true;
    }
}
