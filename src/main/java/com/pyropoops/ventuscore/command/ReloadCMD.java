package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class ReloadCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!VentusCore.permissionManager.hasPermission(sender, Permissions.RELOAD.value(), true, false)) return true;
        VentusCore.reload();
        sender.sendMessage(Methods.color("&aVentus-Core Plugin Reloaded Successfully."));
        return true;
    }

}
