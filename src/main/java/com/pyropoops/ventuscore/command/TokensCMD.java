package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class TokensCMD implements CommandExecutor, TabCompleter {
    public TokensCMD() {
        Objects.requireNonNull(VentusCore.instance.getCommand("tokens")).setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!VentusCore.permissionManager.hasPermission(sender, Permissions.TOKENS.value(), true, false)) return true;
        if (args.length < 2 || !firstArgsList().contains(args[0].toLowerCase())) {
            sender.sendMessage(Methods.color("&cUsage - /tokens <set|give|remove> <player> <number>"));
            return true;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(Methods.color("&4Unable to find that player!"));
            return true;
        }
        int number;
        try {
            number = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage(Methods.color("&cUsage - /tokens <set|give|remove> <player> <number>"));
            return true;
        }
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        switch (args[0].toLowerCase()) {
            case "set":
                dataHandler.setTokens(player, number);
                sender.sendMessage(Methods.color("&aSuccessfully set " + player.getName() + "'s tokens to " + number));
                break;
            case "give":
                sender.sendMessage(Methods.color("&aSuccessfully given " + player.getName() + " " + number + " tokens!"));
                dataHandler.setTokens(player, dataHandler.getTokens(player) + number);
                break;
            case "remove":
                sender.sendMessage(Methods.color("Removed " + number + " tokens from " + player.getName()));
                dataHandler.setTokens(player, dataHandler.getTokens(player) - number);
                break;
            default:
                sender.sendMessage(Methods.color("&cUsage - /tokens <set|give|remove> <player> <number>"));
                break;
        }
        return true;
    }

    private List<String> firstArgsList() {
        return Arrays.asList("set", "give", "remove");
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return args.length == 1 ? firstArgsList() : null;
    }
}
