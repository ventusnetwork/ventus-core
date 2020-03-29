package com.pyropoops.ventuscore.item.movement;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.nms.NMSHandler;
import com.pyropoops.ventuscore.utils.IPlayerTickUpdater;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.List;

public class RocketBootsItem extends Item implements IPlayerTickUpdater, Listener {
    private double uses = 1000D;
    private String fuelKey = "rocket-fuel";

    public RocketBootsItem() {
        super("rocket-boots", Material.DIAMOND_BOOTS, (short) 0, Methods.colour("&4&lROCKET BOOTS"), generateLore());
        VentusCore.tickUpdaterHandler.register(this);
    }

    private static List<String> generateLore() {
        return null;
    }

    @Override
    public ItemStack getItem() {
        return NMSHandler.writeNBT(super.getItem(), fuelKey, Double.toString(uses));
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player player = e.getPlayer();
        if (this.isItem(player.getInventory().getBoots()) && hasFuel(player.getInventory().getBoots()) && player.isSneaking()) {
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.ORANGE, 20);
            player.spawnParticle(Particle.REDSTONE, player.getLocation().add(new Vector(0, 1, 0)), 1, dustOptions);
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player) e.getEntity();
        if (this.isItem(player.getInventory().getBoots()) && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    @Override
    public void onPlayerUpdate(Player player) {
        if (this.isItem(player.getInventory().getBoots()) && player.isSneaking()) {
            ItemStack boots = player.getInventory().getBoots();
            if (boots == null) return;
            if (hasFuel(boots)) {
                Double fuel = getFuel(boots);
                player.setVelocity(player.getLocation().getDirection().multiply(1.75D).add(player.getVelocity()));
                boots = setFuel(boots, fuel - 1D);
                double multiplier = this.getMaterial().getMaxDurability() / uses;
                short durability = (short) (this.getMaterial().getMaxDurability() - (this.getFuel(boots) * multiplier));
                boots.setDurability(durability);
                player.getInventory().setBoots(boots);
            } else {
                player.sendMessage(Methods.colour("&cYour Rocket Boots have ran out of fuel!"));
            }
        }
    }


    private Double getFuel(ItemStack i) {
        String batteryString = NMSHandler.readNBT(i, fuelKey);
        try {
            return Double.parseDouble(batteryString);
        } catch (NumberFormatException ignored) {
            return -1D;
        }
    }

    private ItemStack setFuel(ItemStack i, Double value) {
        return NMSHandler.writeNBT(i, fuelKey, Double.toString(value));
    }

    private boolean hasFuel(ItemStack i) {
        return getFuel(i) > 0;
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getTier() {
        return 2;
    }
}
