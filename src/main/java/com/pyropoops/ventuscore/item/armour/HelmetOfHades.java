package com.pyropoops.ventuscore.item.armour;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.IPlayerTickUpdater;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;

public class HelmetOfHades extends Item implements IPlayerTickUpdater {
    private ArrayList<Player> users;

    public HelmetOfHades(){
        super("hades-helmet", Material.LEATHER_HELMET, (byte) 0, Methods.color("&6&lHELMET OF HADES"), null);
        VentusCore.tickUpdaterHandler.register(this);
        this.users = new ArrayList<>();
    }

    public void setInvisible(Player player, boolean value){
        PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false);
        if(value){
            if(!player.isInvulnerable())
                player.setInvulnerable(true);
            if(!player.hasPotionEffect(PotionEffectType.INVISIBILITY))
                player.addPotionEffect(effect);
        }else{
            if(player.isInvulnerable())
                player.setInvulnerable(false);
            if(player.hasPotionEffect(PotionEffectType.INVISIBILITY))
                player.removePotionEffect(PotionEffectType.INVISIBILITY);
        }
    }

    @Override
    protected ItemStack constructItem(){
        ItemStack i = super.constructItem();
        LeatherArmorMeta meta = (LeatherArmorMeta) i.getItemMeta();
        if(meta != null){
            meta.setColor(Color.fromRGB(200, 50, 10));
            i.setItemMeta(meta);
        }
        return i;
    }


    @Override
    public void onPlayerUpdate(Player player){
        if(!player.isSneaking()){
            if(this.users.contains(player)){
                setInvisible(player, false);
                this.users.remove(player);
            }
            return;
        }

        if(isItem(player.getInventory().getHelmet())){
            setInvisible(player, true);
            Location playerLoc = player.getLocation().clone().add(new Vector(0, 1, 0));
            boolean dark = false;
            for(double x = playerLoc.getX() - 0.5D; x < playerLoc.getX() + 0.5D; x += 0.2D){
                for(double y = playerLoc.getY() - 0.5D; y < playerLoc.getY() + 0.5D; y += 0.2D){
                    for(double z = playerLoc.getZ() - 0.5D; z < playerLoc.getZ() + 0.5D; z += 0.2D){
                        Location loc = new Location(player.getLocation().getWorld(), x, y, z);
                        double distance = loc.distance(player.getLocation());
                        if(distance >= 0.5D){
                            Color color = dark ? Color.fromRGB(60, 60, 60) : Color.fromRGB(30, 30, 30);
                            Particle.DustOptions dustOptions = new Particle.DustOptions(color, 2);
                            loc.getWorld().spawnParticle(Particle.REDSTONE, loc, 1, dustOptions);
                            dark = !dark;
                        }
                    }
                }
            }
            player.setVelocity(player.getLocation().getDirection());
        }
    }

    @Override
    public boolean isHidden(){
        return true;
    }

    @Override
    public int getTier(){
        return 6;
    }
}
