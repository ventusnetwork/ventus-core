package com.pyropoops.ventuscore.utils;

import org.bukkit.entity.Player;

public interface IPlayerTickUpdater extends ITickUpdater {

    void onPlayerUpdate(Player player);

}
