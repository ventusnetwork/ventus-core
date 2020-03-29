package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.gui.tokens.EnchantsGUI;
import com.pyropoops.ventuscore.gui.tokens.SpecialItemsGUI;
import com.pyropoops.ventuscore.gui.tokens.TokensGUI;
import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

public abstract class MenuGUI implements Listener {
    public static MainMenuGUI mainMenuGUI;
    public static TokensGUI tokensGUI;
    public static EnchantsGUI enchantsGUI;
    public static WarpsGUI warpsGUI;
    public static SpecialItemsGUI specialItemsGUI;

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

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if (inv == null) return;

        if (event.getView().getTitle().equals(this.getId())) {
            event.setCancelled(true);
            this.handleInventoryClick(player, event.getSlot(), event.getClick());
        }
    }

    public abstract void handleInventoryClick(Player player, int slot, ClickType type);

    public static void registerMenus() {
        mainMenuGUI = new MainMenuGUI();
        tokensGUI = new TokensGUI();
        enchantsGUI = new EnchantsGUI();
        warpsGUI = new WarpsGUI();
        specialItemsGUI = new SpecialItemsGUI();
    }
}
