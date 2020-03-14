package com.pyropoops.ventuscore.item;

import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.nms.NMSHandler;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Item {
    private ItemStack item;

    public static List<Item> items = new ArrayList<>();
    public static String key = "ventusitem";

    private String registryName;
    private Material material;
    private short data;
    private String displayName;
    private List<String> lore;

    public Item(String registryName, Material material, short data, String displayName, List<String> lore) {
        this.registryName = registryName;
        this.material = material;
        this.data = data;
        this.displayName = displayName;
        this.lore = lore;

        this.item = constructItem();

        if (this instanceof Listener) {
            PluginHelper.registerListener((Listener) this);
        }
        items.add(this);
    }

    public static Item getItem(String registryName) {
        for (Item item : items) {
            if (item.getRegistryName().equalsIgnoreCase(registryName)) {
                return item;
            }
        }
        return null;
    }

    public String getRegistryName() {
        return registryName;
    }

    public void setRegistryName(String registryName) {
        this.registryName = registryName;
    }

    public ItemStack getItem() {
        return this.item;
    }

    private ItemStack constructItem() {
        ItemStack item = new ItemStack(material, 1, data);
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            if (lore != null)
                meta.setLore(lore);
            item.setItemMeta(meta);
        }
        item = NMSHandler.addNBTValue(item, key, this.registryName);
        return item;
    }

    public boolean isItem(ItemStack item) {
        return NMSHandler.itemContainsNBTValue(item, key, this.getRegistryName());
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public short getData() {
        return data;
    }

    public void setData(short data) {
        this.data = data;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }
}
