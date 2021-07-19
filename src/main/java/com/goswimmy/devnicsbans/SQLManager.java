package com.goswimmy.devnicsbans;

import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;
import java.util.logging.Level;

public class SQLManager {

    public static Connection con;

    // connect
    public static boolean connect() {
        FileConfiguration c = DataManager.config;
        String host = c.getString("mysql.host");
        String port = c.getString("mysql.port");
        String database = c.getString("mysql.database");
        String username = c.getString("mysql.username");
        String password = c.getString("mysql.password");
        String ssl = c.getString("mysql.ssl");
        String autoreconnect = c.getString("mysql.autoreconnect");
        if (!isConnected()) {
            try {
                con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database
                        + "?autoReconnect=" + autoreconnect + "&useSSL=" + ssl, username, password);
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    // disconnect
    public static void disconnect() {
        if (isConnected()) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // isConnected
    public static boolean isConnected() {
        return (con != null);
    }

    // getConnection
    public static Connection getConnection() {
        return con;
    }

    // onEnable
    public static void initialize() {
        if (SQLManager.connect()) {
            if (SQLManager.isConnected()) {
                Connection connection = SQLManager.getConnection();
                try {
                    String sql = "CREATE TABLE IF NOT EXISTS `data` (\n" +
                            "\t`id` INT(255) NOT NULL AUTO_INCREMENT,\n" +
                            "\t`uuid` VARCHAR(255) NOT NULL,\n" +
                            "\t`reason` TEXT(1024) NOT NULL DEFAULT 'The ban hammer has spoken!',\n" +
                            "\t`time` BIGINT(255) NOT NULL DEFAULT '0',\n" +
                            "\tPRIMARY KEY (`id`)\n" +
                            ");";
                    PreparedStatement stmt = connection.prepareStatement(sql);
                    stmt.executeUpdate();
                    StringManager.log(Level.INFO, "&eSQLManager loaded!");
                } catch (Exception e) {
                    e.printStackTrace();
                    StringManager.log(Level.WARNING, "&cCould not connect to the Database!");
                }
            }
        }
    }
}