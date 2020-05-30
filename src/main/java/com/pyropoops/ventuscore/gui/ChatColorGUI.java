package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.PermissionManager;
import com.pyropoops.ventuscore.permission.Permissions;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;


public class ChatColorGUI extends MenuGUI {
    public ChatColorGUI(String id) {
        super(id);
    }

    @Override
    public Inventory constructMenu(Player player) {
        return null;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {

    }

    private boolean playerHasPermission(Player player, ChatColor color) {
        PermissionManager pm = VentusCore.permissionManager;
        return pm.hasPermission(player, Permissions.CHAT_COLOR_PREFIX + color.name(), false, false) ||
                pm.hasPermission(player, Permissions.CHAT_COLOR_PREFIX + "all", false, false);
    }
}
