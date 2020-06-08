package com.pyropoops.ventuscore.gui.tokens;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.gui.MenuGUI;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class SpecialItemsGUI extends MenuGUI {
    private HashMap<String, Integer> pages;

    public SpecialItemsGUI() {
        super(Methods.color("&3&lVENTUS SPECIAL ITEMS"));
        this.pages = new HashMap<>();
    }

    public Inventory constructMenu(Player player, int page) {
        Inventory inventory = Bukkit.createInventory(null, 9, this.getId());
        int itemIndex = (page - 1) * 8;

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 8) {
                ItemStack pageItem = new ItemStack(Material.PAPER, 1);
                ItemMeta pageMeta = pageItem.getItemMeta();
                String display = Methods.color("&c&lPAGE " + page + " - CHANGE PAGE");
                this.pages.put(display, page);
                pageMeta.setDisplayName(display);
                pageMeta.setLore(Arrays.asList(Methods.color("&cLeft Click: Next page"), Methods.color("&cRight Click: Previous page")));
                pageItem.setItemMeta(pageMeta);
                inventory.setItem(i, pageItem);
            } else if (itemIndex < Item.items.size() && !Item.items.get(itemIndex).isHidden()) {
                Item item = Item.items.get(itemIndex);
                ItemStack itemStack = new ItemStack(item.getMaterial(), 1);
                ItemMeta meta = itemStack.getItemMeta();
                if (meta instanceof Damageable) {
                    ((Damageable) meta).damage(item.getData());
                }
                meta.setDisplayName(item.getDisplayName());
                meta.setLore(Arrays.asList(Methods.color("&cPrice: &4" + item.getPrice() + " tokens"),
                        Methods.color("&cTier: &4" + item.getTier())));
                itemStack.setItemMeta(meta);
                inventory.setItem(i, itemStack);
                itemIndex++;
            } else {
                inventory.setItem(i, filler);
            }
        }
        return inventory;
    }

    @Override
    public Inventory constructMenu(Player player) {
        return this.constructMenu(player, 1);
    }

    private void purchase(Player player, Item item) {
        int price = item.getPrice();
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        if (dataHandler.getTokens(player) < price) {
            player.sendMessage(Methods.color("&cYou cannot afford that!"));
            return;
        }
        if (player.getInventory().firstEmpty() == -1) {
            player.sendMessage(Methods.color("&4Your inventory is full!"));
            return;
        }
        dataHandler.setTokens(player, dataHandler.getTokens(player) - price);
        player.getInventory().addItem(item.getItem());
        player.sendMessage(Methods.color("&aPurchase successful!"));
    }

    private Item getItemFromDisplay(String displayName) {
        for (Item item : Item.items) {
            if (item.getDisplayName().equals(displayName)) {
                return item;
            }
        }
        return null;
    }

    private int getChangedPage(int currentPage, ClickType click) {
        int size = Item.items.size();
        int pages = 0;
        while (size > 0) {
            size -= 8;
            pages++;
        }
        if (click.isLeftClick()) {
            if (currentPage + 1 > pages) {
                return 1;
            }
            return currentPage + 1;
        } else if (click.isRightClick()) {
            if (currentPage - 1 < 1) {
                return pages;
            }
            return currentPage - 1;
        }
        return currentPage;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        Inventory inv = player.getOpenInventory().getTopInventory();
        ItemMeta meta = inv.getItem(slot).getItemMeta();
        if (slot != 8) {
            Item item = getItemFromDisplay(meta.getDisplayName());
            if (item != null) {
                purchase(player, item);
                if (type == ClickType.LEFT)
                    player.closeInventory();
            }
        } else {
            int page = this.pages.get(meta.getDisplayName());
            player.openInventory(this.constructMenu(player, getChangedPage(page, type)));
        }
    }
}
