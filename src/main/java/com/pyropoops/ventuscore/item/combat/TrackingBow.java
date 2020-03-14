package com.pyropoops.ventuscore.item.combat;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.misc.GenanItem;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class TrackingBow extends Item implements Listener {
    private double range = 30D;

    public TrackingBow() {
        super("trackingbow", Material.BOW, (short) 0, Methods.colour("&6&lTRACKING BOW"), generateLore()); }

    public static List<String> generateLore() {
        return new ArrayList<>();
    }

    @EventHandler
    public void onProjectileLaunch(EntityShootBowEvent e) {
        if (!this.isItem(e.getBow())) return;
        final double[] tickMultiplier = {0.1D};
        new BukkitRunnable() {
            @Override
            public void run() {
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (e.getProjectile().isDead() || e.getProjectile().isOnGround()) {
                            this.cancel();
                        }
                        double targetDistance = range;
                        LivingEntity target = null;
                        for (Entity entity : e.getProjectile().getNearbyEntities(range, range, range)) {
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
                }.runTaskTimer(VentusCore.instance, 0L, 0L);
            }
        }.runTaskLater(VentusCore.instance, 10L);
    }


}
