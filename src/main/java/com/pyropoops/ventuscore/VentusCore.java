package com.pyropoops.ventuscore;

import com.pyropoops.ventuscore.command.GetItemCommand;
import com.pyropoops.ventuscore.config.ConfigHandler;
import com.pyropoops.ventuscore.data.PlayerDataHandler;
import com.pyropoops.ventuscore.gui.MenuGUI;
import com.pyropoops.ventuscore.helper.PluginHelper;
import com.pyropoops.ventuscore.item.armour.ScubaArmour;
import com.pyropoops.ventuscore.item.combat.BackstabberItem;
import com.pyropoops.ventuscore.item.combat.TrackingBow;
import com.pyropoops.ventuscore.item.misc.GenanItem;
import com.pyropoops.ventuscore.item.movement.GrapplingHookItem;
import com.pyropoops.ventuscore.item.movement.MagicCarpetItem;
import com.pyropoops.ventuscore.item.movement.RocketBootsItem;
import com.pyropoops.ventuscore.permission.PermissionManager;
import com.pyropoops.ventuscore.utils.TickUpdaterHandler;
import org.bukkit.plugin.java.JavaPlugin;

public final class VentusCore extends JavaPlugin {
    public static VentusCore instance;
    public static PermissionManager permissionManager;
    public static TickUpdaterHandler tickUpdaterHandler;
    public PlayerDataHandler playerDataHandler;

    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;

        this.register();
    }


    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void register() {
        tickUpdaterHandler = new TickUpdaterHandler();

        registerCommands();
        registerData();
        registerPermissions();
        registerCommands();
        registerConfigs();
        registerMenus();
        registerItems();
    }

    private void registerCommands() {
        GetItemCommand getItemCommand = new GetItemCommand();
        PluginHelper.registerCommand("getitem", getItemCommand);
        getCommand("getitem").setTabCompleter(getItemCommand);
    }

    private void registerData() {
        playerDataHandler = new PlayerDataHandler(this.getDataFolder() + "/player-data");
    }


    private void registerItems() {
        GrapplingHookItem grapplingHookItem = new GrapplingHookItem();
        RocketBootsItem rocketBootsItem = new RocketBootsItem();
        TrackingBow trackingBow = new TrackingBow();
        MagicCarpetItem magicCarpetItem = new MagicCarpetItem();
        ScubaArmour scubaArmour = new ScubaArmour();
        BackstabberItem backstabberItem = new BackstabberItem();
        GenanItem genanItem = new GenanItem();
    }

    private void registerConfigs() {
        ConfigHandler.initConfigs();
    }

    private void registerMenus() {
        MenuGUI.registerMenus();
    }

    private void registerPermissions() {
        permissionManager = new PermissionManager(null);
    }
}
