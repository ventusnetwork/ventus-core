package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GetItemCMD implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!VentusCore.permissionManager.hasPermission(sender,
                Permissions.GET_ITEM.value(), true, false)) return true;

        if (args.length == 0) {
            correctUsage(sender);
            return true;
        }

        Item item = Item.getItem(args[0]);
        if (item == null) {
            sender.sendMessage(Methods.color("&cError: &4Could not find that item!"));
            return true;
        }

        if (args.length == 1) {
            if (!(sender instanceof Player)) {
                correctUsage(sender);
                return true;
            }

            this.givePlayerItem((Player) sender, item);
            return true;
        }

        Player target = Bukkit.getPlayer(args[1]);
        if (target == null) {
            Methods.color("&cError: &4Could not find that player!");
            return true;
        }
        sender.sendMessage(Methods.color("&aYou have given &7" + target.getName() + "&a the item: &7" + item.getDisplayName() + "&a!"));
        givePlayerItem(target, item);
        return true;
    }

    private void givePlayerItem(Player player, Item item) {
        player.getInventory().addItem(item.getItem());
        player.sendMessage(Methods.color("&aYou have received the item: &7" + item.getDisplayName() + "&a!"));
    }

    private void correctUsage(CommandSender sender) {
        String message;
        if (sender instanceof Player) {
            message = Methods.color("&cCorrect Usage - /getitem <item name> [player]");
        } else {
            message = Methods.color("&cCorrect Usage - /getitem <item name> <player>");
        }
        sender.sendMessage(message);
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            ArrayList<String> strings = new ArrayList<>();
            for (Item item : Item.items) {
                strings.add(item.getRegistryName());
            }
            return strings;
        }
        return null;
    }
}