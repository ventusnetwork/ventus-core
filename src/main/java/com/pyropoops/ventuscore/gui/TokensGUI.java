package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class TokensGUI extends MenuGUI {
    public TokensGUI() {
        super(Methods.colour("&2&lVENTUS TOKENS"));
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 27, this.getId());
        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 11) {
                // Enchanted book - &3&lENCHANTS
                ItemStack enchants = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta enchantsMeta = enchants.getItemMeta();
                enchantsMeta.setDisplayName(Methods.colour("&3&lENCHANTS"));
                enchants.setItemMeta(enchantsMeta);
                inventory.setItem(i, enchants);
            } else if (i == 13) {
                // Emerald - &2&lTOKENS - &aYou have {tokens} tokens!
                ItemStack tokens = new ItemStack(Material.EMERALD, 1);
                ItemMeta tokensMeta = tokens.getItemMeta();
                tokensMeta.setDisplayName(Methods.colour("&2&lTOKENS"));
                tokensMeta.setLore(Arrays.asList(Methods.colour("&aYou have " +
                        VentusCore.instance.playerDataHandler.getTokens(player) + " tokens!")));
                tokens.setItemMeta(tokensMeta);
                inventory.setItem(i, tokens);
            } else if (i == 15) {
                // Nether Star - &7&lSPECIAL ITEMS
                ItemStack items = new ItemStack(Material.NETHER_STAR, 1);
                ItemMeta itemsMeta = items.getItemMeta();
                itemsMeta.setDisplayName(Methods.colour("&7&lSPECIAL ITEMS"));
                items.setItemMeta(itemsMeta);
                inventory.setItem(i, items);
            } else {
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            }
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        if (slot == 11) enchants(player);
    }

    private void enchants(Player player) {
        player.openInventory(enchantsGUI.constructMenu(player));
    }
}
