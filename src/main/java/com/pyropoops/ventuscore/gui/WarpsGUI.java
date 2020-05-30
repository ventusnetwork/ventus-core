package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class WarpsGUI extends MenuGUI {
    public Inventory inventory;
    private HashMap<Integer, ItemStack> items;
    private HashMap<Integer, String> warpNames;

    public WarpsGUI() {
        super(Methods.colour("&2&lVENTUS - WARPS"));

        this.items = new HashMap<>();
        this.warpNames = new HashMap<>();

        this.initWarps();

        this.inventory = this.constructMenu(null);
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, this.getId());

        for (int i : items.keySet()) {
            if (items.containsKey(i)) {
                ItemStack item = items.get(i);
                inventory.setItem(i, item);
            } else {
                inventory.setItem(i, filler);
            }
        }

        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        if (warpNames.containsKey(slot)) {
            player.closeInventory();
            player.performCommand("warp " + warpNames.get(slot));
        }
    }

    private void registerWarp(String warp, int slot, Material material, String displayName, List<String> lore) {
        ItemStack i = new ItemStack(material, 1);
        ItemMeta meta = i.getItemMeta();
        meta.setDisplayName(displayName);
        meta.setLore(lore);
        i.setItemMeta(meta);

        items.put(slot, i);
        warpNames.put(slot, warp);
    }

    private void initWarps() {
    }
}
