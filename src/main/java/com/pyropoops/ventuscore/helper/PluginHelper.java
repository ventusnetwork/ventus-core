package com.pyropoops.ventuscore.helper;

import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class PluginHelper {

    public static void registerListener(Listener listener) {
        Plugin plugin = VentusCore.instance;
        plugin.getServer().getPluginManager().registerEvents(listener, plugin);
    }

    public static PluginCommand getCommand(String name, CommandExecutor executor) {
        PluginCommand command = null;
        try {
            Constructor<PluginCommand> c = PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
            c.setAccessible(true);
            command = c.newInstance(name, VentusCore.instance);
            command.setExecutor(executor);
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException
                | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return command;
    }

    public static void registerCommand(String s, String s1, CommandExecutor executor) {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            commandMap.register(s, s1, getCommand(s, executor));

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void registerCommand(String s, CommandExecutor executor) {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            commandMap.register(s, VentusCore.instance.getName(), getCommand(s, executor));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void registerCommand(String s, String s1, PluginCommand pluginCommand) {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            commandMap.register(s, s1, pluginCommand);

        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static void registerCommand(String s, PluginCommand pluginCommand) {
        try {
            Field f = Bukkit.getPluginManager().getClass().getDeclaredField("commandMap");
            f.setAccessible(true);
            CommandMap commandMap = (CommandMap) f.get(Bukkit.getPluginManager());
            commandMap.register(s, VentusCore.instance.getName(), pluginCommand);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}
