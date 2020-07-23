package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.gui.tokens.EnchantsGUI;
import com.pyropoops.ventuscore.gui.tokens.SpecialItemsGUI;
import com.pyropoops.ventuscore.gui.tokens.TokensGUI;
import com.pyropoops.ventuscore.helper.PluginHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class MenuGUI implements Listener {
    public static MainMenuGUI mainMenuGUI;
    public static TokensGUI tokensGUI;
    public static EnchantsGUI enchantsGUI;
    public static SpecialItemsGUI specialItemsGUI;
    public static PlayerStatsGUI playerStatsGUI;
    public static CratesGUI cratesGUI;

    protected static ItemStack filler;
    private final String id;

    public MenuGUI(String id){
        this.id = id;
        PluginHelper.registerListener(this);
    }

    public static void registerMenus(){
        filler = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
        ItemMeta fillerMeta = filler.getItemMeta();
        if(fillerMeta == null) return;
        fillerMeta.setDisplayName(" ");
        filler.setItemMeta(fillerMeta);

        mainMenuGUI = new MainMenuGUI();
        tokensGUI = new TokensGUI();
        enchantsGUI = new EnchantsGUI();
        specialItemsGUI = new SpecialItemsGUI();
        playerStatsGUI = new PlayerStatsGUI();
        cratesGUI = new CratesGUI();
    }

    public String getId(){
        return id;
    }

    public abstract Inventory constructMenu(Player player);

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        Player player = (Player) event.getWhoClicked();
        Inventory inv = event.getClickedInventory();
        if(inv == null) return;

        if(event.getView().getTitle().equals(this.getId())){
            event.setCancelled(true);
            this.handleInventoryClick(player, event.getSlot(), event.getClick());
        }
    }

    public abstract void handleInventoryClick(Player player, int slot, ClickType type);
}
