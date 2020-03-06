package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryInteractEvent;
import org.bukkit.inventory.Inventory;

public abstract class MenuGUI implements Listener {
    public static MainMenuGUI mainMenuGUI;
    public static TokensGUI tokensGUI;

    private String id;

    public MenuGUI(String id) {
        this.id = id;
        PluginHelper.registerListener(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public abstract Inventory constructMenu(Player player);

    public boolean hasInventoryOpen(Player player) {
        return player.getOpenInventory().getTitle().equals(this.getId());
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;

        if (this.hasInventoryOpen(player)) {
            event.setCancelled(true);
            this.handleInventoryClick(player, event.getSlot(), event.getClick());
        }
    }

    public abstract void handleInventoryClick(Player player, int slot, ClickType type);

    public static void registerMenus() {
        mainMenuGUI = new MainMenuGUI();
        tokensGUI = new TokensGUI();
    }
}
