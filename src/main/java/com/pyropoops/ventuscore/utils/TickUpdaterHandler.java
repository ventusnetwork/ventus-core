package com.pyropoops.ventuscore.utils;

import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;

public class TickUpdaterHandler extends BukkitRunnable {
    private ArrayList<ITickUpdater> updaters = new ArrayList<>();

    public TickUpdaterHandler() {
        this.runTaskTimer(VentusCore.instance, 0L, 0L);
    }

    public void register(ITickUpdater iTickUpdater) {
        updaters.add(iTickUpdater);
    }

    @Override
    public void run() {
        for (ITickUpdater updater : updaters) {
            if (updater instanceof IServerTickUpdater) {
                ((IServerTickUpdater) updater).onUpdate();
            }
            if (updater instanceof IPlayerTickUpdater) {
                for (Player player : VentusCore.instance.getServer().getOnlinePlayers()) {
                    ((IPlayerTickUpdater) updater).onPlayerUpdate(player);
                }
            }
        }
    }
}
