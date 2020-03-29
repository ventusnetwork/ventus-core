package com.pyropoops.ventuscore.item.movement;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Arrow;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class NimbusBroomItem extends Item implements Listener {
    public NimbusBroomItem() {
        super("nimbus-broom", Material.WOODEN_SHOVEL, (short) 0, Methods.colour("&4&lNIMBUS 2000"), generateLore());
    }

    private static List<String> generateLore() {
        return null;
    }

    @EventHandler
    public void onClick(PlayerInteractEvent e) {
        if (this.isItem(e.getItem()) && (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            Location loc = e.getPlayer().getLocation();
            Arrow arrow = loc.getWorld().spawn(loc, Arrow.class);
            arrow.setShooter(e.getPlayer());
            arrow.addPassenger(e.getPlayer());
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!arrow.getPassengers().contains(e.getPlayer())) {
                        arrow.remove();
                        this.cancel();
                    }
                    if (arrow.isOnGround() || arrow.isDead()) {
                        arrow.remove();
                        this.cancel();
                    }
                    arrow.setVelocity(e.getPlayer().getLocation().getDirection().multiply(4));
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.GRAY, 10);
                    e.getPlayer().spawnParticle(Particle.REDSTONE, arrow.getLocation(), 1, dustOptions);
                }
            }.runTaskTimer(VentusCore.instance, 0L, 0L);
        }
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public int getTier() {
        return 5;
    }
}
