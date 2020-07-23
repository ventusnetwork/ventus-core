package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.gui.MenuGUI;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CratesCMD implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, String[] strings) {
        if(!(sender instanceof Player)) {
            sender.sendMessage(Methods.color("&cYou must be a player to do this!"));
            return true;
        }
        Player player = (Player) sender;
        player.openInventory(MenuGUI.cratesGUI.inventory);
        return true;
    }
}
