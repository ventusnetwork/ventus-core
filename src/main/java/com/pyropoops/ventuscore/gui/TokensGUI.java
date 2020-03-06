package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class TokensGUI extends MenuGUI {
    public TokensGUI() {
        super(Methods.colour("&2&lTOKENS"));
    }

    @Override
    public Inventory constructMenu(Player player) {
        return Bukkit.createInventory(null, 27, this.getId());
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {

    }
}
