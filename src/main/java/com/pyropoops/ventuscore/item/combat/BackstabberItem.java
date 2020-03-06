package com.pyropoops.ventuscore.item.combat;

import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.util.Vector;

import java.util.HashMap;
import java.util.List;

public class BackstabberItem extends Item implements Listener {
    private long cooldown = 5000L;

    private HashMap<Player, Long> cooldownTime;

    public BackstabberItem() {
        super("backstabber", Material.DIAMOND_SWORD, (short) 0, Methods.colour("&4&lBACKSTABBER"), generateLore());
        cooldownTime = new HashMap<>();
    }

    private static List<String> generateLore() {
        return null;
    }

    @EventHandler
    public void onSneak(EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof LivingEntity) || !(e.getDamager() instanceof Player)) return;
        Player player = (Player) e.getDamager();
        LivingEntity target = (LivingEntity) e.getEntity();
        if (playerCanUseItem(player)) {
            teleport(player, target);
        }
    }

    private boolean playerCanUseItem(Player player) {
        if (this.isItem(player.getInventory().getItemInMainHand()) && player.isSneaking()) {
            long now = System.currentTimeMillis();
            if (cooldownTime.containsKey(player) && cooldownTime.get(player) + cooldown > now) {
                int seconds = (int) ((cooldownTime.get(player) + cooldown - now) / 1000);
                player.sendMessage(Methods.colour("&cYou cannot do that yet! You have " +
                        seconds + " seconds left on your cooldown!"));
                return false;
            }
            cooldownTime.put(player, System.currentTimeMillis());
            return true;
        }
        return false;
    }


    private void teleport(Player player, LivingEntity target) {
        final Location playerLoc = player.getLocation();

        Vector direction = target.getLocation().getDirection().multiply(-1D);
        final Location targetLoc = target.getLocation().add(direction);
        targetLoc.setY(target.getLocation().getY());

        player.teleport(targetLoc);

        createParticlesBubble(playerLoc);
        createParticlesBubble(targetLoc);
    }

    private void createParticlesBubble(Location loc) {
        for (double x = loc.getX() - 2; x < loc.getX() + 2; x++) {
            for (double y = loc.getY() - 2; y < loc.getY() + 2; y++) {
                for (double z = loc.getZ() - 2; z < loc.getZ() + 2; z++) {
                    Location location = new Location(loc.getWorld(), x, y, z);
                    Particle.DustOptions dustOptions = new Particle.DustOptions(Color.RED, 4);
                    location.getWorld().spawnParticle(Particle.REDSTONE, location, 1, dustOptions);
                }
            }
        }
    }

}
