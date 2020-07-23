package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetLevelCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!VentusCore.permissionManager.hasPermission(sender, Permissions.LEVEL.value(), true, false)) return true;
        if(args.length < 2){
            sender.sendMessage(Methods.color("&cCorrect Usage - /setlevel <player> <level>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(Methods.color("&cError: &4Unable to find that player!"));
            return true;
        }
        int level;
        try{
            level = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            sender.sendMessage(Methods.color("&cCorrect Usage - /setlevel <player> <level>"));
            return true;
        }
        VentusCore.instance.playerDataHandler.setLevel(target, level);
        sender.sendMessage(Methods.color("&aSuccessfully set &2" + target.getName() + "&a's level to &2" + level + "&a."));
        return true;
    }
}
