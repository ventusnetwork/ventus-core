package com.pyropoops.ventuscore.item.armour;

import com.pyropoops.ventuscore.VentusCore;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.IPlayerTickUpdater;
import com.pyropoops.ventuscore.utils.Methods;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class ScubaArmour implements IPlayerTickUpdater {
    private ScubaHelmet scubaHelmet;
    private ScubaChestplate scubaChestplate;
    private ScubaLeggings scubaLeggings;
    private ScubaBoots scubaBoots;

    public ScubaArmour() {
        scubaHelmet = new ScubaHelmet();
        scubaChestplate = new ScubaChestplate();
        scubaLeggings = new ScubaLeggings();
        scubaBoots = new ScubaBoots();

        VentusCore.tickUpdaterHandler.register(this);
    }

    public static List<String> generateLore() {
        return new ArrayList<>();
    }

    private boolean isWearing(Player player) {
        return scubaHelmet.isItem(player.getInventory().getHelmet())
                && scubaChestplate.isItem(player.getInventory().getChestplate())
                && scubaLeggings.isItem(player.getInventory().getLeggings())
                && scubaBoots.isItem(player.getInventory().getBoots());
    }

    private boolean isPlayerUnderwater(Player player) {
        return player.getLocation().add(new Vector(0, 1, 0)).getBlock().getType() == Material.WATER;
    }

    @Override
    public void onPlayerUpdate(Player player) {
        if (this.isWearing(player) && this.isPlayerUnderwater(player)) {
            if (player.isSneaking())
                player.setVelocity(player.getLocation().getDirection().multiply(1.1D));
            player.setRemainingAir(player.getMaximumAir());
            player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 220, 10, false, false));
        }
    }
}

class ScubaHelmet extends Item {
    public ScubaHelmet() {
        super("scubahelmet", Material.DIAMOND_HELMET, (short) 0, Methods.colour("&b&lSCUBA HELMET"), ScubaArmour.generateLore());
    }
}

class ScubaChestplate extends Item {
    public ScubaChestplate() {
        super("scubachestplate", Material.DIAMOND_CHESTPLATE, (short) 0, Methods.colour("&b&lSCUBA CHESTPLATE"), ScubaArmour.generateLore());
    }
}

class ScubaLeggings extends Item {
    public ScubaLeggings() {
        super("scubaleggings", Material.DIAMOND_LEGGINGS, (short) 0, Methods.colour("&b&lSCUBA LEGGINGS"), ScubaArmour.generateLore());
    }
}

class ScubaBoots extends Item {
    public ScubaBoots() {
        super("scubaboots", Material.DIAMOND_BOOTS, (short) 0, Methods.colour("&b&lSCUBA BOOTS"), ScubaArmour.generateLore());
    }
}
