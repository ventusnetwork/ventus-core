package com.pyropoops.ventuscore.config;

import com.google.common.base.Charsets;
import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Config {
    private File file;
    private FileConfiguration config;

    private String name;
    private String folder;
    private JavaPlugin plugin;

    public Config(String name, String folder) {
        if (!name.endsWith(".yml")) {
            name += ".yml";
        }

        this.name = name;
        this.folder = folder;
        this.plugin = VentusCore.instance;

        loadConfig();
        ConfigHandler.registerConfig(this);
    }

    public Config(String name, String folder, InputStream stream) {
        if (!name.endsWith(".yml")) {
            name += ".yml";
        }

        this.name = name;
        this.folder = folder;
        this.plugin = VentusCore.instance;

        loadConfig(stream);
        ConfigHandler.registerConfig(this);
    }

    public String getName() {
        return name;
    }

    public String getFolder() {
        return folder;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    public File getFile() {
        return file;
    }

    public void saveConfig() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadConfig() {
        try {
            file = folder != null ? new File(plugin.getDataFolder() + "/" + folder + "/" + name) : new File(plugin.getDataFolder() + "/" + name);
            config = YamlConfiguration.loadConfiguration(file);
            InputStream defConfigStream = folder != null ? plugin.getResource(folder + "/" + name) : plugin.getResource(name);
            if (!file.exists()) {
                if (folder != null) {
                    plugin.saveResource(folder + "/" + name, false);
                } else {
                    plugin.saveResource(name, false);
                }
            }
            if (defConfigStream != null) {
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
                config.options().copyDefaults(true);
            }
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadConfig(InputStream defConfigStream) {
        try {
            file = folder != null ? new File(plugin.getDataFolder() + "/" + folder + "/" + name) : new File(plugin.getDataFolder() + "/" + name);
            config = YamlConfiguration.loadConfiguration(file);
            if (!file.exists()) {
                if (!file.getParentFile().exists()) {
                    file.getParentFile().mkdirs();
                }
                file.createNewFile();
            }
            if (defConfigStream != null) {
                config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream, Charsets.UTF_8)));
                config.options().copyDefaults(true);
            }
            saveConfig();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}