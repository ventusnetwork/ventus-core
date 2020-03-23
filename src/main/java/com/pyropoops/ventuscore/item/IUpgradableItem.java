package com.pyropoops.ventuscore.item;

import org.bukkit.inventory.ItemStack;

public interface IUpgradableItem {

    int getMaxLevel();

    int getLevel(ItemStack itemStack);

    ItemStack setLevel(ItemStack itemStack, int level);

    ItemStack update(ItemStack itemStack);

    ItemStack levelUp(ItemStack itemStack);
}
