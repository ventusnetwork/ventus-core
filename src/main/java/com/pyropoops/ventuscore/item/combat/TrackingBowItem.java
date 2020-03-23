package com.pyropoops.ventuscore.item.combat;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.nms.NMSHandler;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TrackingBowItem extends Item implements Listener {
    private double range = 50D;

    public TrackingBowItem() {
        super("trackingbow", Material.BOW, (short) 0, Methods.colour("&6&lTRACKING BOW"), generateLore());
    }

    public static List<String> generateLore() {
        return new ArrayList<>();
    }

    @Override
    public ItemStack getItem() {
        return NMSHandler.writeNBT(super.getItem(), "range", "30");
    }

    @EventHandler
    public void onProjectileLaunch(EntityShootBowEvent e) {
        if (!this.isItem(e.getBow())) return;
        final double[] tickMultiplier = {0.1D};

        double[] range = new double[1];

        try {
            range[0] = Double.parseDouble(NMSHandler.readNBT(e.getBow(), "range"));
        } catch (NumberFormatException | NullPointerException ex) {
            range[0] = 30D;
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (e.getProjectile().isDead() || e.getProjectile().isOnGround()) {
                    this.cancel();
                }

                if (!(e.getEntity() instanceof Player) || ((Player) e.getEntity()).isSneaking()) {
                    double targetDistance = range[0];
                    LivingEntity target = null;
                    for (Entity entity : e.getProjectile().getNearbyEntities(range[0], range[0], range[0])) {
                        if (!(entity instanceof LivingEntity)) continue;
                        if (entity == e.getEntity()) continue;
                        LivingEntity livingEntity = (LivingEntity) entity;
                        if (e.getProjectile().getLocation().distance(livingEntity.getLocation()) < targetDistance) {
                            targetDistance = e.getProjectile().getLocation().distance(livingEntity.getLocation());
                            target = livingEntity;
                        }
                    }
                    if (target == null) return;
                    Vector direction = target.getLocation().clone().toVector().subtract(e.getProjectile().getLocation().toVector()).normalize();
                    e.getProjectile().setVelocity(direction.multiply(tickMultiplier[0]));
                    tickMultiplier[0] += 0.1D;
                }
            }
        }.runTaskTimer(VentusCore.instance, 0L, 0L);

    }


}
