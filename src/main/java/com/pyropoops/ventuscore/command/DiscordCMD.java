package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

public class DiscordCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(VentusCore.permissionManager.hasPermission(commandSender, Permissions.DISCORD.value(), true, false))
            commandSender.sendMessage(Methods.color("&9You can find our discord at: &nhttp://discord.ventusnetwork.net/"));
        return true;
    }
}
