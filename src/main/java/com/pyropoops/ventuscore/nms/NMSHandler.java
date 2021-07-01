package com.pyropoops.ventuscore.nms;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSHandler {

    public static String readNBT(ItemStack item, String key) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = nmsItem.getOrCreateTag();
        return nbt.getString(key);
    }


    public static ItemStack writeNBT(ItemStack item, String key, String value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = nmsItem.getOrCreateTag();
        nbt.setString(key, value);
        nmsItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static ItemStack addNBTValue(ItemStack item, String key, String value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = nmsItem.getOrCreateTag();
        NBTTagList list = nbt.getList(key, 8);
        list.add(list.size(), NBTTagString.a(value));
        nbt.set(key, list);
        nmsItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    public static boolean itemContainsNBTValue(ItemStack item, String key, String value) {
        net.minecraft.world.item.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = nmsItem.getOrCreateTag();
        NBTTagList list = nbt.getList(key, 8);
        return list.contains(NBTTagString.a(value));
    }
}
