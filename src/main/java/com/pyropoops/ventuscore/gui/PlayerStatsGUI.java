package com.pyropoops.ventuscore.gui;

import com.earth2me.essentials.Essentials;
import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PlayerStatsGUI extends MenuGUI {
    private Essentials ess;

    public PlayerStatsGUI() {
        super(Methods.color("&3&L" + "PLAYER STATS"));
        this.hookEssentials();
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, this.getId());

        for (int i = 0; i < inventory.getSize(); i++) {
            if (i == 0) {
                ItemStack balance = new ItemStack(Material.PAPER, 1);
                ItemMeta balanceMeta = balance.getItemMeta();
                if (balanceMeta == null) continue;
                balanceMeta.setDisplayName(Methods.color("&a&L" + "BALANCE"));
                balanceMeta.setLore(getBalanceLore(player));
                balance.setItemMeta(balanceMeta);
                inventory.setItem(i, balance);
            } else if (i == 2) {
                ItemStack tokens = new ItemStack(Material.EMERALD, 1);
                ItemMeta tokensMeta = tokens.getItemMeta();
                if (tokensMeta == null) continue;
                tokensMeta.setDisplayName(Methods.color("&2&lTOKENS"));
                tokensMeta.setLore(getTokensLore(player));
                tokens.setItemMeta(tokensMeta);
                inventory.setItem(i, tokens);
            } else if (i == 4) {
                ItemStack level = new ItemStack(Material.BOOK, 1);
                ItemMeta levelMeta = level.getItemMeta();
                if (levelMeta == null) continue;
                levelMeta.setDisplayName(Methods.color("&9&lLEVEL"));
                levelMeta.setLore(getLevelLore(player));
                level.setItemMeta(levelMeta);
                inventory.setItem(i, level);
            } else if (i == 6) {
                ItemStack kills = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta killsMeta = kills.getItemMeta();
                if (killsMeta == null) continue;
                killsMeta.setDisplayName(Methods.color("&4&lKILLS"));
                killsMeta.setLore(getKillsLore(player));
                kills.setItemMeta(killsMeta);
                inventory.setItem(i, kills);
            } else if (i == 8) {
                ItemStack playTime = new ItemStack(Material.DIAMOND_SWORD, 1);
                ItemMeta playTimeMeta = playTime.getItemMeta();
                if (playTimeMeta == null) continue;
                playTimeMeta.setDisplayName(Methods.color("&9&lPLAY TIME"));
                playTimeMeta.setLore(getPlaytimeLore(player));
                playTime.setItemMeta(playTimeMeta);
                inventory.setItem(i, playTime);
            } else {
                inventory.setItem(i, filler);
            }
        }

        return inventory;
    }

    private void hookEssentials() {
        if (VentusCore.instance.getServer().getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials) VentusCore.instance.getServer().getPluginManager().getPlugin("Essentials");
        }
    }

    private List<String> getBalanceLore(Player player) {
        BigDecimal money = ess.getUser(player).getMoney();
        if (money.doubleValue() > 0) {
            DecimalFormat decimalFormat = new DecimalFormat("#,###.00");
            String s = decimalFormat.format(money);
            return Collections.singletonList(Methods.color("&aYou have &2$" + s + "&a."));
        }
        return Collections.singletonList(Methods.color("&aYou have &2$0&a."));
    }

    private List<String> getTokensLore(Player player) {
        int tokens = VentusCore.instance.playerDataHandler.getTokens(player);
        return Collections.singletonList(Methods.color("&aYou have &2" + tokens + " &a" + "tokens."));
    }

    private List<String> getLevelLore(Player player) {
        int level = VentusCore.instance.playerDataHandler.getLevel(player);
        return Collections.singletonList(Methods.color("&aYou are &2level " + level + "&a."));
    }

    private List<String> getKillsLore(Player player) {
        int kills = VentusCore.instance.playerDataHandler.getKills(player);
        String s = "&aYou have &2" + kills + "&a kill";
        if (kills != 1) s += "s";
        return Collections.singletonList(Methods.color(s + "."));
    }

    private List<String> getPlaytimeLore(Player player) {
        int minutes = VentusCore.instance.playerDataHandler.getMinutes(player);
        int hours = (int) Math.floor(minutes / 60D);
        int days = (int) Math.floor(hours / 24D);
        hours = hours % 24;
        minutes = minutes % 60;
        List<String> lore = new ArrayList<>();
        lore.add(Methods.color("&aYou have played for:"));
        lore.add(Methods.color("&a" + days + " days"));
        lore.add(Methods.color("&a" + hours + " hours"));
        lore.add(Methods.color("&aand " + minutes + " minutes"));
        return lore;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
    }

}
