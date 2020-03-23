package com.pyropoops.ventuscore.command;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.gui.MenuGUI;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainMenuCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (VentusCore.permissionManager.hasPermission(sender, Permissions.MAIN_MENU.value(), true, false))
            menu(sender);
        return true;
    }

    private void menu(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(Methods.colour("&cYou must be a player to run this command!"));
            return;
        }
        Player player = (Player) sender;
        player.openInventory(MenuGUI.mainMenuGUI.constructMenu(player));
    }
}
