package com.pyropoops.ventuscore.nms;

import net.minecraft.server.v1_16_R2.NBTTagCompound;
import net.minecraft.server.v1_16_R2.NBTTagList;
import net.minecraft.server.v1_16_R2.NBTTagString;
import org.bukkit.craftbukkit.v1_16_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class NMSHandler {

    public static String readNBT(ItemStack item, String key) {
        net.minecraft.server.v1_16_R2.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = NMSItem.getOrCreateTag();
        return nbt.getString(key);
    }


    public static ItemStack writeNBT(ItemStack item, String key, String value) {
        net.minecraft.server.v1_16_R2.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = NMSItem.getOrCreateTag();
        nbt.setString(key, value);
        NMSItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(NMSItem);
    }

    public static ItemStack addNBTValue(ItemStack item, String key, String value) {
        net.minecraft.server.v1_16_R2.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = NMSItem.getOrCreateTag();
        NBTTagList list = nbt.getList(key, 8) == null ? new NBTTagList() : nbt.getList(key, 8);
        list.add(list.size(), NBTTagString.a(value));
        nbt.set(key, list);
        NMSItem.setTag(nbt);
        return CraftItemStack.asBukkitCopy(NMSItem);
    }

    public static boolean itemContainsNBTValue(ItemStack item, String key, String value) {
        net.minecraft.server.v1_16_R2.ItemStack NMSItem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound nbt = NMSItem.getOrCreateTag();
        NBTTagList list = nbt.getList(key, 8) == null ? new NBTTagList() : nbt.getList(key, 8);
        return list.contains(NBTTagString.a(value));
    }
}
