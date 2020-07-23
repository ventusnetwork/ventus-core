package com.pyropoops.ventuscore.item.movement;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import org.apache.commons.lang.Validate;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class GrapplingHookItem extends Item implements Listener {
    private HashMap<Entity, LivingEntity> grappleArrows;
    private ArrayList<Entity> grappleArrowDamageEvents;

    public GrapplingHookItem() {
        super("grapplinghook", Material.BOW, (short) 0, Methods.color("&2&lGRAPPLING HOOK"), generateLore());
        grappleArrows = new HashMap<>();
        grappleArrowDamageEvents = new ArrayList<>();
    }

    public static List<String> generateLore() {
        return Arrays.asList(Methods.color("&aTier 4"), Methods.color("&aUse this to grapple from place to place!"));
    }

    @Override
    public ItemStack getItem() {
        ItemStack i = super.getItem();
        i.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        return i;
    }

    @EventHandler(ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent e) {
        if (grappleArrowDamageEvents.contains(e.getEntity()) && e.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
            e.setCancelled(true);
            grappleArrowDamageEvents.remove(e.getEntity());
        }
    }

    @EventHandler
    public void onShootBow(EntityShootBowEvent e) {
        if (this.isItem(e.getBow())) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    LivingEntity entity = e.getEntity();
                    Entity arrow = e.getProjectile();
                    grappleArrows.put(arrow, entity);
                    if (arrow.isDead()) {
                        this.cancel();
                    }
                    if (arrow.isOnGround() && entity.getLocation().distance(arrow.getLocation()) <= 1) {
                        grappleArrows.remove(arrow);
                        arrow.remove();
                        this.cancel();
                    }
                    if (entity instanceof Player && ((Player) entity).isSneaking()) {
                        if (arrow.isOnGround()) {
                            entity.setVelocity(entity.getLocation().getDirection().multiply(2D));
                        }
                        grappleArrows.remove(arrow);
                        arrow.remove();
                        this.cancel();
                    }
                    if (arrow.isOnGround() && !arrow.isDead()) {
                        Vector direction = arrow.getLocation().toVector()
                                .subtract(entity.getLocation().toVector()).normalize();
                        entity.setVelocity(direction.multiply(1.2D));
                            drawLine(e.getEntity().getLocation(),
                                arrow.getLocation(), 0.1D, new Vector(0, 1, 0));
                    }
                }
            }.runTaskTimer(VentusCore.instance, 0L, 0L);
        }
    }

    private void drawLine(Location from, Location to, double space, Vector offset) {
        World world = from.getWorld();
        Validate.isTrue(to.getWorld().equals(world), "Particles cannot be drawn from different worlds!");

        double distance = from.distance(to);

        Vector v1 = from.toVector();
        Vector v2 = to.toVector();

        Vector vector = v2.clone().subtract(v1).normalize().multiply(space);

        for (double covered = 0; covered < distance; covered += space) {
            v1.add(vector);
            Location loc = new Location(world, v1.getX(), v1.getY(), v1.getZ());
            Particle.DustOptions dustOptions = new Particle.DustOptions(Color.fromRGB(30, 150, 0), 1);
            world.spawnParticle(Particle.REDSTONE, loc.add(offset), 1, dustOptions);
        }
    }


    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getTier() {
        return 4;
    }
}
