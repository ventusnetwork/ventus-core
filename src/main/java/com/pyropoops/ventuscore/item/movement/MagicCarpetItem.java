package com.pyropoops.ventuscore.item.movement;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.IPlayerTickUpdater;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MagicCarpetItem extends Item implements Listener, IPlayerTickUpdater {
    private double space = 0.5D;
    private double range = 1.5D;


    public MagicCarpetItem() {
        super("magiccarpet", Material.BLUE_CARPET, (short) 0, Methods.color("&b&l&oMAGIC CARPET"), generateLore());
        VentusCore.tickUpdaterHandler.register(this);
    }

    public static List<String> generateLore() {
        return Arrays.asList(Methods.color("&bTier 5"), Methods.color("&bFly around!"));
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if(this.isItem(e.getItem()))
            e.setCancelled(true);
    }

    @Override
    public void onPlayerUpdate(Player player) {
        if (this.isUsing(player)) {
            player.setVelocity(player.getLocation().getDirection());
            for (double x = player.getLocation().getX() - range; x <= player.getLocation().getX() + range; x += space) {
                for (double z = player.getLocation().getBlockZ() - range; z <= player.getLocation().getZ() + range; z += space) {
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.AQUA, 5);
                    Location loc = new Location(player.getWorld(), x, player.getLocation().getY() - 0.5, z);
                    if (loc.getBlock().getType() == Material.AIR) {
                        player.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, dustOptions);
                    }
                }
            }
        }
    }

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && this.isUsing((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    private boolean isUsing(Player player) {
        return this.isItem(player.getInventory().getItemInMainHand());
    }

    @Override
    public boolean isHidden() {
        return false;
    }

    @Override
    public int getTier() {
        return 5;
    }
}
