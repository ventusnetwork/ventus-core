package com.pyropoops.ventuscore.gui;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.permission.Permissions;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.UUID;

public class MainMenuGUI extends MenuGUI {
    public MainMenuGUI() {
        super(Methods.colour("&4&lVENTUS MENU"));
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, this.getId());

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 11) {
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                skullMeta.setOwningPlayer(player);
                skullMeta.setDisplayName(Methods.colour("&c&l") + player.getName());
                skullMeta.setLore(Arrays.asList(Methods.colour("&cHello! This is your main menu!"),
                        Methods.colour("&cYou can navigate through the menu with"),
                        Methods.colour("&cthe buttons below!")));
                playerHead.setItemMeta(skullMeta);
                inventory.setItem(i, playerHead);
            } else if (i == 29) {
                ItemStack tokens = new ItemStack(Material.EMERALD, 1);
                ItemMeta tokensMeta = tokens.getItemMeta();
                tokensMeta.setDisplayName(Methods.colour("&2&lTOKENS"));
                tokensMeta.setLore(Arrays.asList(Methods.colour("&aYou have " +
                        VentusCore.instance.playerDataHandler.getTokens(player) + " tokens!")));
                tokens.setItemMeta(tokensMeta);
                inventory.setItem(i, tokens);
            } else if (i == 30) {
                ItemStack warps = new ItemStack(Material.ENDER_PEARL, 1);
                ItemMeta warpsMeta = warps.getItemMeta();
                warpsMeta.setDisplayName(Methods.colour("&a&lWARPS"));
                warps.setItemMeta(warpsMeta);
                inventory.setItem(i, warps);
            } else if (i == 31) {
                ItemStack kits = new ItemStack(Material.ARROW, 1);
                ItemMeta kitsMeta = kits.getItemMeta();
                kitsMeta.setDisplayName(Methods.colour("&4&lKITS"));
                kits.setItemMeta(kitsMeta);
                inventory.setItem(i, kits);
            } else if (i == 32) {
                ItemStack quests = new ItemStack(Material.ENCHANTED_BOOK, 1);
                ItemMeta questsMeta = quests.getItemMeta();
                questsMeta.setDisplayName(Methods.colour("&6&lQUESTS"));
                questsMeta.setLore(Arrays.asList(Methods.colour("&4&lWIP")));
                quests.setItemMeta(questsMeta);
                inventory.setItem(i, quests);
            } else if (i == 33) {
                ItemStack playerStats = new ItemStack(Material.WRITABLE_BOOK, 1);
                ItemMeta playerStatsMeta = playerStats.getItemMeta();
                playerStatsMeta.setDisplayName(Methods.colour("&3&lPLAYER STATS"));
                playerStats.setItemMeta(playerStatsMeta);
                inventory.setItem(i, playerStats);
            } else if (i == 38) {
                ItemStack auctionHouse = new ItemStack(Material.PAPER, 1);
                ItemMeta auctionHouseMeta = auctionHouse.getItemMeta();
                auctionHouseMeta.setDisplayName(Methods.colour("&9&lAUCTION HOUSE"));
                auctionHouse.setItemMeta(auctionHouseMeta);
                inventory.setItem(i, auctionHouse);
            } else if (i == 39) {
                ItemStack chatColor = new ItemStack(Material.MAGENTA_DYE, 1);
                ItemMeta chatColorMeta = chatColor.getItemMeta();
                chatColorMeta.setDisplayName(Methods.colour("&d&lCHAT COLOR"));
                chatColor.setItemMeta(chatColorMeta);
                inventory.setItem(i, chatColor);
            } else if (i == 40) {
                ItemStack discord = new ItemStack(Material.OAK_SIGN, 1);
                ItemMeta discordMeta = discord.getItemMeta();
                discordMeta.setDisplayName(Methods.colour("&9&lDISCORD"));
                discord.setItemMeta(discordMeta);
                inventory.setItem(i, discord);
            } else if (i == 41) {
                ItemStack staff = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta staffMeta = (SkullMeta) staff.getItemMeta();
                staffMeta.setOwningPlayer(Bukkit.getOfflinePlayer
                        (UUID.fromString("a83ffbfd-17e1-4d1c-8c97-804c994eb155")));
                staffMeta.setDisplayName(Methods.colour("&4&lSTAFF"));
                staff.setItemMeta(staffMeta);
                inventory.setItem(i, staff);
            } else if (i == 42) {
                ItemStack exit = new ItemStack(Material.BARRIER, 1);
                ItemMeta exitMeta = exit.getItemMeta();
                exitMeta.setDisplayName(Methods.colour("&4&lEXIT"));
                exit.setItemMeta(exitMeta);
                inventory.setItem(i, exit);
            } else {
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
            }
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        if (slot == 29) tokens(player);
        if (slot == 30) warps(player);
        if (slot == 31) kits(player);
        if (slot == 32) quests(player);
        if (slot == 33) playerStats(player);
        if (slot == 38) auctionHouse(player);
        if (slot == 39) chatColor(player);
        if (slot == 40) discord(player);
        if (slot == 41) staff(player);
        if (slot == 42) exit(player);
    }

    private void tokens(Player player) {
        player.openInventory(tokensGUI.constructMenu(player));
    }

    private void warps(Player player) {
        // TODO
        player.openInventory(warpsGUI.inventory);
    }

    private void kits(Player player) {
        // TODO
    }

    private void quests(Player player) {
        // TODO: WIP
    }

    private void playerStats(Player player) {
        // TODO: WIP
    }

    private void auctionHouse(Player player) {
        player.closeInventory();
        player.performCommand("crazyauctions");
    }

    private void chatColor(Player player) {
    }

    private void exit(Player player) {
        player.closeInventory();
    }

    private void staff(Player player) {
    }

    private void discord(Player player) {
        player.closeInventory();
        if (VentusCore.permissionManager.hasPermission(player, Permissions.DISCORD.value(), true, false))
            player.sendMessage(Methods.colour("&9You can find our discord at: n&nhttp://discord.ventusnetwork.net/"));
    }
}