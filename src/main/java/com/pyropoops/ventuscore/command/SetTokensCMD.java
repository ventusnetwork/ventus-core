package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SetTokensCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
        if(!VentusCore.permissionManager.hasPermission(sender, Permissions.TOKENS.value(), true, false)) return true;
        if(args.length < 2){
            sender.sendMessage(Methods.color("&cCorrect Usage - /settokens <player> <tokens>"));
            return true;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null){
            sender.sendMessage(Methods.color("&cError: &4Unable to find that player!"));
            return true;
        }
        int tokens;
        try{
            tokens = Integer.parseInt(args[1]);
        }catch(NumberFormatException e){
            sender.sendMessage(Methods.color("&cCorrect Usage - /settokens <player> <tokens>"));
            return true;
        }
        VentusCore.instance.playerDataHandler.setTokens(target, tokens);
        sender.sendMessage(Methods.color("&aSuccessfully set &2" + target.getName() + "&a's tokens to &2" + tokens + "&a."));
        return true;
    }
}