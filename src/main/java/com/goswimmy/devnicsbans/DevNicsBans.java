package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public final class DevNicsBans extends JavaPlugin {

    public static DevNicsBans pl;

    @Override
    public void onEnable() {
        DevNicsBans.pl = this;
        DataManager.setup(this);

        FileConfiguration c = DataManager.config;

        if(c.getString("storage-type").equals("mysql")){
            SQLManager.initialize();
        } else {
            SQLiteManager.initialize();
            Connection connection = SQLiteManager.getConnection();
            try{
                PreparedStatement ps = connection.prepareStatement("INSERT INTO data (uuid) VALUES (\"4905bd67-0ae6-4a97-8528-ba5248d620b0\")");
                ps.executeUpdate();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new InventoryEvent(), this);
        pm.registerEvents(new JoinEvent(), this);

        getCommand("ban").setExecutor(new BanCommand());
        getCommand("checkban").setExecutor(new CheckBanCommand());
    }

    public static DevNicsBans getPl() {
        return pl;
    }
}
