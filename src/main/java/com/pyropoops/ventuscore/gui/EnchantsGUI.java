package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

import java.util.Collections;
import java.util.HashMap;

public class EnchantsGUI extends MenuGUI {
    private PlayerDataHandler data;
    private HashMap<String, Integer> prices;

    public EnchantsGUI() {
        super(Methods.colour("&3&lVENTUS ENCHANTS"));

        data = VentusCore.instance.playerDataHandler;

        prices = new HashMap<>();

        prices.put("protection", 75);
        prices.put("sharpness", 100);
        prices.put("efficiency", 75);
        prices.put("silktouch", 100);
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, this.getId());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 10) {
                // protection I
                ItemStack protection = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta protectionMeta = protection.getItemMeta();
                protectionMeta.setDisplayName(Methods.colour("&3&lPROTECTION I"));
                protectionMeta.setLore(Collections.singletonList(Methods.colour
                        ("&aPrice: &2" + prices.get("protection") + " tokens")));
                protection.setItemMeta(protectionMeta);
                inventory.setItem(i, protection);
            } else if (i == 12) {
                // sharpness I
                ItemStack sharpness = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta sharpnessMeta = sharpness.getItemMeta();
                sharpnessMeta.setDisplayName(Methods.colour("&3&lSHARPNESS I"));
                sharpnessMeta.setLore(Collections.singletonList(Methods.colour
                        ("&aPrice: &2" + prices.get("sharpness") + " tokens")));
                sharpness.setItemMeta(sharpnessMeta);
                inventory.setItem(i, sharpness);
            } else if (i == 14) {
                // efficiency I
                ItemStack efficiency = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta efficiencyMeta = efficiency.getItemMeta();
                efficiencyMeta.setDisplayName(Methods.colour("&3&lEFFICIENCY I"));
                efficiencyMeta.setLore(Collections.singletonList(Methods.colour
                        ("&aPrice: &2" + prices.get("efficiency") + " tokens")));
                efficiency.setItemMeta(efficiencyMeta);
                inventory.setItem(i, efficiency);
            } else if (i == 16) {
                // silk touch I
                ItemStack silk = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta silkMeta = silk.getItemMeta();
                silkMeta.setDisplayName(Methods.colour("&3&lSILK TOUCH I"));
                silkMeta.setLore(Collections.singletonList(Methods.colour
                        ("&aPrice: &2" + prices.get("silktouch") + " tokens")));
                silk.setItemMeta(silkMeta);
                inventory.setItem(i, silk);
            } else {
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            }
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        if (slot == 10) protection(player);
    }

    private void protection(Player player) {
        int price = prices.get("protection");
        if (handlePrice(player, prices.get("protection"))) {
            data.setTokens(player, (int) data.getTokens(player) - price);
            ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) i.getItemMeta();
            meta.addStoredEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, true);
            i.setItemMeta(meta);
            player.getInventory().addItem(i);
            player.sendMessage(Methods.colour("&aThank you!"));
        }
    }

    private void sharpness(Player player) {
    }

    private void efficiency(Player player) {
    }

    private void silkTouch(Player player) {
    }

    private boolean handlePrice(Player player, int price) {
        if ((int) data.getTokens(player) < price) {
            player.sendMessage(Methods.colour("&cError: &4You do not have enough tokens for that!"));
            return false;
        }
        return true;
    }
}
