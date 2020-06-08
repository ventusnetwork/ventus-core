package com.pyropoops.ventuscore.gui.tokens;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.gui.MenuGUI;
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

import java.util.Collections;
import java.util.HashMap;

public class EnchantsGUI extends MenuGUI {
    public Inventory inventory;

    private PlayerDataHandler data;
    private HashMap<Enchantment, Integer> prices;

    public EnchantsGUI() {
        super(Methods.color("&3&lVENTUS ENCHANTS"));

        data = VentusCore.instance.playerDataHandler;

        prices = new HashMap<>();

        prices.put(Enchantment.PROTECTION_ENVIRONMENTAL, 75);
        prices.put(Enchantment.DAMAGE_ALL, 100);
        prices.put(Enchantment.DIG_SPEED, 75);
        prices.put(Enchantment.SILK_TOUCH, 100);

        this.inventory = this.constructMenu(null);
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, this.getId());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 10) {
                // protection I
                ItemStack protection = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta protectionMeta = protection.getItemMeta();
                protectionMeta.setDisplayName(Methods.color("&3&lPROTECTION I"));
                protectionMeta.setLore(Collections.singletonList(Methods.color
                        ("&aPrice: &2" + prices.get(Enchantment.PROTECTION_ENVIRONMENTAL) + " tokens")));
                protection.setItemMeta(protectionMeta);
                inventory.setItem(i, protection);
            } else if (i == 12) {
                // sharpness I
                ItemStack sharpness = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta sharpnessMeta = sharpness.getItemMeta();
                sharpnessMeta.setDisplayName(Methods.color("&3&lSHARPNESS I"));
                sharpnessMeta.setLore(Collections.singletonList(Methods.color
                        ("&aPrice: &2" + prices.get(Enchantment.DAMAGE_ALL) + " tokens")));
                sharpness.setItemMeta(sharpnessMeta);
                inventory.setItem(i, sharpness);
            } else if (i == 14) {
                // efficiency I
                ItemStack efficiency = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta efficiencyMeta = efficiency.getItemMeta();
                efficiencyMeta.setDisplayName(Methods.color("&3&lEFFICIENCY I"));
                efficiencyMeta.setLore(Collections.singletonList(Methods.color
                        ("&aPrice: &2" + prices.get(Enchantment.DIG_SPEED) + " tokens")));
                efficiency.setItemMeta(efficiencyMeta);
                inventory.setItem(i, efficiency);
            } else if (i == 16) {
                // silk touch I
                ItemStack silk = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta silkMeta = silk.getItemMeta();
                silkMeta.setDisplayName(Methods.color("&3&lSILK TOUCH I"));
                silkMeta.setLore(Collections.singletonList(Methods.color
                        ("&aPrice: &2" + prices.get(Enchantment.SILK_TOUCH) + " tokens")));
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
        if (slot == 10) this.purchase(player, Enchantment.PROTECTION_ENVIRONMENTAL);
        if (slot == 12) this.purchase(player, Enchantment.DAMAGE_ALL);
        if (slot == 14) this.purchase(player, Enchantment.DIG_SPEED);
        if (slot == 16) this.purchase(player, Enchantment.SILK_TOUCH);
    }

    private void purchase(Player player, Enchantment enchantment) {
        if (handlePrice(player, prices.get(enchantment))) {
            ItemStack i = new ItemStack(Material.ENCHANTED_BOOK, 1);
            EnchantmentStorageMeta meta = (EnchantmentStorageMeta) i.getItemMeta();
            meta.addStoredEnchant(enchantment, 1, true);
            i.setItemMeta(meta);
            player.getInventory().addItem(i);
            player.sendMessage(Methods.color("&aItem bought successfully!"));
        }
    }

    private boolean handlePrice(Player player, int price) {
        if (data.getTokens(player) < price) {
            player.sendMessage(Methods.color("&cError: &4You do not have enough tokens for that!"));
            return false;
        }
        data.setTokens(player, data.getTokens(player) - price);
        return true;
    }
}
