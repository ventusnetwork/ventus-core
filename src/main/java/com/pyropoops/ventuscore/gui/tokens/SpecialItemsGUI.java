package com.pyropoops.ventuscore.gui.tokens;

import com.pyropoops.ventuscore.gui.MenuGUI;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;

public class SpecialItemsGUI extends MenuGUI {
    public Inventory inventory;

    public SpecialItemsGUI() {
        super(Methods.colour("&3&lVENTUS SPECIAL ITEMS"));

        this.inventory = this.constructMenu(null);
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, this.getId());
        for (int i = 0; i < inventory.getSize(); i++) {
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {

    }
}
