package com.goswimmy.devnicsbans;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Level;

public class SQLiteManager {

    public static Connection connection;

    public SQLiteManager(DevNicsBans instance) {

    }

    public static Connection getConnection() {
        File dataFolder = new File(DevNicsBans.getPl().getDataFolder(), "data.db");
        if(!dataFolder.exists()) {
            try{
                dataFolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try{
            if(connection!=null&&!connection.isClosed()) {
                return connection;
            }
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:"+dataFolder);
            return connection;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ResultSet getQuery(String sql) {
        connection = SQLiteManager.getConnection();
        try{
            PreparedStatement ps = connection.prepareStatement(sql);
            return ps.executeQuery();
        } catch (SQLException ex) {
            ex.printStackTrace();
            StringManager.log(Level.WARNING, "&cCould not connect to the Database!");
        }
        return null;
    }

    public static void initialize() {
        connection = getConnection();
        try {
            Statement s = connection.createStatement();
            s.executeUpdate("CREATE TABLE IF NOT EXISTS `data` (\n" +
                    "\t`id` INT(255) UNIQUE,\n" +
                    "\t`uuid` VARCHAR(255) NOT NULL,\n" +
                    "\t`reason` TEXT(1024) DEFAULT 'The ban hammer has spoken!',\n" +
                    "\t`time` BIGINT(255) DEFAULT '0',\n" +
                    "\tPRIMARY KEY (`id`)\n" +
                    ");");
            s.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
