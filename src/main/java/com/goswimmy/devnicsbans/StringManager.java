package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.logging.Level;

public class StringManager {
    public static String color(String raw) {
        return ChatColor.translateAlternateColorCodes('&', raw);
    }

    public static void log(Level lvl, String raw) {
        if(lvl == Level.INFO) {
            Bukkit.getLogger().log(Level.INFO, StringManager.color("&7[&a&l✓&7] &e"+raw));
        } else {
            Bukkit.getLogger().log(Level.INFO, StringManager.color("&7[&c&lX&7] &e"+raw));
        }
    }
}