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
import java.util.Collections;

public class MainMenuGUI extends MenuGUI {
    public MainMenuGUI(){
        super(Methods.color("&4&lVENTUS MENU"));
    }

    @Override
    public Inventory constructMenu(Player player){
        Inventory inventory = Bukkit.createInventory(null, 54, this.getId());

        for(int i = 0; i < inventory.getSize(); i++){
            if(i == 11){
                ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD, 1);
                SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
                if(skullMeta == null) continue;
                skullMeta.setOwningPlayer(player);
                skullMeta.setDisplayName(Methods.color("&c&l") + player.getName());
                skullMeta.setLore(Arrays.asList(Methods.color("&cHello! This is your main menu!"),
                        Methods.color("&cYou can navigate through the menu with"),
                        Methods.color("&c" + "the buttons below!")));
                playerHead.setItemMeta(skullMeta);
                inventory.setItem(i, playerHead);
            }else if(i == 29){
                ItemStack tokens = new ItemStack(Material.EMERALD, 1);
                ItemMeta tokensMeta = tokens.getItemMeta();
                if(tokensMeta == null) continue;
                tokensMeta.setDisplayName(Methods.color("&2&lTOKENS"));
                tokensMeta.setLore(Collections.singletonList(Methods.color("&aYou have " +
                        VentusCore.instance.playerDataHandler.getTokens(player) + " tokens!")));
                tokens.setItemMeta(tokensMeta);
                inventory.setItem(i, tokens);
            }else if(i == 30){
                ItemStack playerStats = new ItemStack(Material.WRITABLE_BOOK, 1);
                ItemMeta playerStatsMeta = playerStats.getItemMeta();
                if(playerStatsMeta == null) continue;
                playerStatsMeta.setDisplayName(Methods.color("&3&lPLAYER STATS"));
                playerStats.setItemMeta(playerStatsMeta);
                inventory.setItem(i, playerStats);
            }else if(i == 31){
                ItemStack auctionHouse = new ItemStack(Material.PAPER, 1);
                ItemMeta auctionHouseMeta = auctionHouse.getItemMeta();
                if(auctionHouseMeta == null) continue;
                auctionHouseMeta.setDisplayName(Methods.color("&9&lAUCTION HOUSE"));
                auctionHouse.setItemMeta(auctionHouseMeta);
                inventory.setItem(i, auctionHouse);
            }else if(i == 32){
                ItemStack discord = new ItemStack(Material.OAK_SIGN, 1);
                ItemMeta discordMeta = discord.getItemMeta();
                if(discordMeta == null) continue;
                discordMeta.setDisplayName(Methods.color("&9&lDISCORD"));
                discord.setItemMeta(discordMeta);
                inventory.setItem(i, discord);
            }else if(i == 33){
                ItemStack exit = new ItemStack(Material.BARRIER, 1);
                ItemMeta exitMeta = exit.getItemMeta();
                if(exitMeta == null) continue;
                exitMeta.setDisplayName(Methods.color("&4&lEXIT"));
                exit.setItemMeta(exitMeta);
                inventory.setItem(i, exit);
            }else{
                inventory.setItem(i, filler);
            }
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type){
        if(slot == 29) tokens(player);
        if(slot == 30) playerStats(player);
        if(slot == 31) auctionHouse(player);
        if(slot == 32) discord(player);
        if(slot == 33) exit(player);
    }

    private void tokens(Player player){
        player.openInventory(tokensGUI.constructMenu(player));
    }


    private void playerStats(Player player){
        player.openInventory(MenuGUI.playerStatsGUI.constructMenu(player));
    }

    private void auctionHouse(Player player){
        player.closeInventory();
        player.performCommand("crazyauctions");
    }

    private void exit(Player player){
        player.closeInventory();
    }

    private void discord(Player player){
        player.closeInventory();
        if(VentusCore.permissionManager.hasPermission(player, Permissions.DISCORD.value(), true, false))
            player.sendMessage(Methods.color("&9You can find our discord at: &nhttp://discord.ventusnetwork.net/"));
    }
}