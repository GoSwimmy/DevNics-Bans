package com.goswimmy.devnicsbans;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class DataManager {

    public static FileConfiguration config;

    public static File configFile;

    public static void setup(Plugin pl) {
        configFile = new File(pl.getDataFolder(), "config.yml");
        config = pl.getConfig();
        config.options().copyDefaults(false);
        if (!pl.getDataFolder().exists()) {
            pl.getDataFolder().mkdir();
        }
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pl.saveResource("config.yml", true);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public static void saveConfig() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}