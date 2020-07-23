package com.pyropoops.ventuscore.gui;

import com.earth2me.essentials.Essentials;
import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CratesGUI extends MenuGUI {
    public Inventory inventory;
    private Essentials ess;

    public CratesGUI() {
        super(Methods.color("&6&lCRATES"));
        hook();
        inventory = constructMenu(null);
    }

    private List<String> getNames() {
        String[] keys = {
                "&6Token Gambling Crate Key",
                "&aSteeds Crate Key",
                "&aPets and Farm Animals Crate Key",
                "&aExotic Animals Crate Key"
        };
        List<String> list = new ArrayList<>();
        for (String key : keys) {
            list.add(Methods.color(key));
        }
        return list;
    }

    private void hook() {
        if (VentusCore.instance.getServer().getPluginManager().isPluginEnabled("Essentials")) {
            this.ess = (Essentials) VentusCore.instance.getServer().getPluginManager().getPlugin("Essentials");
        }
    }

    private boolean canAffordTokens(Player player, int amount) {
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        int bal = dataHandler.getTokens(player);
        if (bal < amount) {
            player.closeInventory();
            player.sendMessage(Methods.color("&cYou do not have enough tokens for that!"));
            return false;
        }
        return true;
    }

    private boolean canAffordMoney(Player player, float amount) {
        float bal = this.ess.getUser(player).getMoney().floatValue();
        if (bal < amount) {
            player.closeInventory();
            player.sendMessage(Methods.color("&cYou do not have enough money for that!"));
            return false;
        }
        return true;
    }

    private boolean hasSpace(Inventory inventory) {
        return inventory.firstEmpty() != -1;
    }

    private void token(Player player) {
        PlayerDataHandler dataHandler = VentusCore.instance.playerDataHandler;
        if (canAffordTokens(player, 25)) {
            dataHandler.setTokens(player, dataHandler.getTokens(player) - 25);
            player.sendMessage(Methods.color("&aYou have bought one token gambling crate key for 25 tokens!"));
            player.closeInventory();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give to " + player.getName() + " TokenGamblingCrate");
        }
    }

    private void steeds(Player player) {
        if (canAffordMoney(player, 10000)) {
            this.ess.getUser(player).takeMoney(BigDecimal.valueOf(10000));
            player.sendMessage(Methods.color("&aYou have bought one steeds crate key for $10,000"));
            player.closeInventory();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give to " + player.getName() + " SteedsCrate");
        }
    }

    private void pets(Player player) {
        if (canAffordMoney(player, 15000)) {
            this.ess.getUser(player).takeMoney(BigDecimal.valueOf(15000));
            player.sendMessage(Methods.color("&aYou have bought one pets and farm animals crate key for $15,000"));
            player.closeInventory();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give to " + player.getName() + " PetsAndFarmCrate");
        }
    }

    private void exotic(Player player) {
        if (canAffordMoney(player, 75000)) {
            this.ess.getUser(player).takeMoney(BigDecimal.valueOf(75000));
            player.sendMessage(Methods.color("&aYou have bought one exotic animals crate key for $75,000"));
            player.closeInventory();
            Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), "crate give to " + player.getName() + " ExoticAnimalsCrate");
        }
    }

    private List<String> getPrices() {
        return Arrays.asList("25 Tokens", "$10,000", "$15,000", "$75,000");
    }

    @Override
    public Inventory constructMenu(Player player) {
        Inventory inventory = Bukkit.createInventory(null, 9, this.getId());
        int i = 0;
        for (int j = 0; j < 9; j++) {
            if (j == 1 || j == 3 || j == 5 || j == 7) {
                String name = getNames().get(i);
                List<String> lore = Collections.singletonList(Methods.color("&a" + getPrices().get(i)));
                ItemStack itemStack = new ItemStack(Material.TRIPWIRE_HOOK, 1);
                ItemMeta itemMeta = itemStack.getItemMeta();
                if (itemMeta == null) continue;
                itemMeta.setDisplayName(name);
                itemMeta.setLore(lore);
                itemStack.setItemMeta(itemMeta);
                inventory.setItem(j, itemStack);
                i++;
            }
        }
        return inventory;
    }

    @Override
    public void handleInventoryClick(Player player, int slot, ClickType type) {
        if (!hasSpace(player.getInventory())) {
            player.sendMessage(Methods.color("&cYou do not have space in your inventory!"));
            return;
        }
        switch (slot) {
            case 1:
                token(player);
                break;
            case 3:
                steeds(player);
                break;
            case 5:
                pets(player);
                break;
            case 7:
                exotic(player);
                break;
            default:
                break;
        }
    }
}
