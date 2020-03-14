package com.pyropoops.ventuscore.config;

import com.pyropoops.ventuscore.VentusCore;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.util.HashMap;

public class ConfigHandler {
    public static Config mainConfig;

    private static HashMap<String, Config> configs = new HashMap<>();

    public static void registerConfig(Config config) {
        configs.put(config.getFolder() + "/" + config.getName().toLowerCase(), config);
    }

    public static FileConfiguration getConfig(String configName) {
        if (configs.containsKey(configName.toLowerCase())) {
            return configs.get(configName).getConfig();
        }
        return null;
    }

    public static File getFile(String fileName) {
        if (configs.containsKey(fileName.toLowerCase())) {
            return configs.get(fileName).getFile();
        }
        return null;
    }

    public static void initConfigs() {
        mainConfig = new Config("config.yml", null);
    }
}
