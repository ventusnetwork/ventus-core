package com.pyropoops.ventuscore.item.tools;

import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.item.Item;
import com.pyropoops.ventuscore.utils.Methods;
import net.minecraft.server.v1_15_R1.BlockPosition;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.ArrayList;
import java.util.List;

public class DrillPickaxeItem extends Item implements Listener {
    private final int RANGE = 1;
    private List<Location> drilledLocations;

    public DrillPickaxeItem(){
        super("drillpickaxe", Material.DIAMOND_PICKAXE, (short) 0, Methods.color("&4&lDRILL PICKAXE"), null);
        this.drilledLocations = new ArrayList<>();
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e){
        if(drilledLocations.contains(e.getBlock().getLocation())){
            drilledLocations.remove(e.getBlock().getLocation());
            return;
        }
        if(this.isItem(e.getPlayer().getInventory().getItemInMainHand())){
            breakBlocks(e.getBlock().getLocation(), RANGE, e.getPlayer());
        }
    }

    private boolean isBlockUnbreakable(Block block){
        return ConfigHandler.mainConfig.getConfig().getStringList("drill-pickaxe-unbreakable-blocks")
                .contains(block.getType().name());
    }

    private void breakBlocks(Location loc, int range, Player player){
        for(int x = loc.getBlockX() - range; x <= loc.getBlockX() + range; x++){
            for(int y = loc.getBlockY() - range; y <= loc.getBlockY() + range; y++){
                for(int z = loc.getBlockZ() - range; z <= loc.getBlockZ() + range; z++){
                    Block block = player.getWorld().getBlockAt(x, y, z);
                    if(isBlockUnbreakable(block))
                        continue;
                    drilledLocations.add(block.getLocation());
                    this.breakBlock(block, player);
                }
            }
        }
    }

    private void breakBlock(Block b, Player player){
        if(b.getType() != Material.AIR){
            ((CraftPlayer) player).getHandle().playerInteractManager.breakBlock(new BlockPosition(b.getX(), b.getY(), b.getZ()));
        }
    }

    @Override
    public boolean isHidden(){
        return false;
    }

    @Override
    public int getTier(){
        return 4;
    }
}
