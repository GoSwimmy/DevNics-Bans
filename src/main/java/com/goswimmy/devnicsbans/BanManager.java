package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;
import java.util.logging.Level;

public class BanManager {



    public static long toMilliSec(String s) {
        // From: http://stackoverflow.com/a/8270824
        String[] sl = s.toLowerCase().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
        long i = Long.parseLong(sl[0]);
        switch (sl[1]) {
            case "s":
                return i * 1000;
            case "m":
                return i * 1000 * 60;
            case "h":
                return i * 1000 * 60 * 60;
            case "d":
                return i * 1000 * 60 * 60 * 24;
            case "w":
                return i * 1000 * 60 * 60 * 24 * 7;
            case "mo":
                return i * 1000 * 60 * 60 * 24 * 30;
            default:
                return -1;
        }
    }

    public static boolean isBanned(Player p) {
        return isBanned(Bukkit.getOfflinePlayer(p.getUniqueId()));
    }

    public static boolean isBanned(OfflinePlayer p) {
        FileConfiguration c = DataManager.config;
        if(c.getString("storage-type").equals("mysql")) {
            if (SQLManager.connect()) {
                if (SQLManager.isConnected()) {
                    Connection connection = SQLManager.getConnection();
                    try {
                        String sqlinsert = "SELECT * FROM data WHERE uuid = ?";
                        PreparedStatement stmtinsert = connection.prepareStatement(sqlinsert);
                        stmtinsert.setString(1, p.getUniqueId().toString());
                        ResultSet res = stmtinsert.executeQuery();
                        if(res.next()) {
                            if(res.getLong("time") != 0 && res.getLong("time") <= System.currentTimeMillis()) {
                                String sqlremove = "DELETE FROM data WHERE uuid = ?";
                                PreparedStatement stmtremove = connection.prepareStatement(sqlremove);
                                stmtremove.setString(1, p.getUniqueId().toString());
                                stmtremove.executeUpdate();
                                return false;
                            } else {
                                return true;
                            }
                        } else {
                            return false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringManager.log(Level.WARNING, "&cCould not connect to the Database!");
                    }
                }
            }
        } else {
            Connection con = SQLiteManager.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM data WHERE uuid = ?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet res = ps.executeQuery();
                if(res.next()) {
                    if(res.getLong("time") != 0 && res.getLong("time") <= System.currentTimeMillis()) {
                        String sqlremove = "DELETE FROM data WHERE uuid = ?";
                        PreparedStatement stmtremove = con.prepareStatement(sqlremove);
                        stmtremove.setString(1, p.getUniqueId().toString());
                        stmtremove.executeUpdate();
                        return false;
                    } else {
                        return true;
                    }
                } else {
                    return false;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    public static String getReason(Player p) {
        FileConfiguration c = DataManager.config;
        if(c.getString("storage-type").equals("mysql")) {
            if (SQLManager.connect()) {
                if (SQLManager.isConnected()) {
                    Connection connection = SQLManager.getConnection();
                    try {
                        String sqlinsert = "SELECT * FROM data WHERE uuid = ?";
                        PreparedStatement stmtinsert = connection.prepareStatement(sqlinsert);
                        stmtinsert.setString(1, p.getUniqueId().toString());
                        ResultSet res = stmtinsert.executeQuery();
                        if(res.next()) {
                            return res.getString("reason");
                        } else {
                            return null;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringManager.log(Level.WARNING, "&cCould not connect to the Database!");
                    }
                }
            }
        } else {
            Connection con = SQLiteManager.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement("SELECT * FROM data WHERE uuid = ?");
                ps.setString(1, p.getUniqueId().toString());
                ResultSet res = ps.executeQuery();
                if(res.next()) {
                    return res.getString("reason");
                } else {
                    return null;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }

    public static void banPlayer(Player p, Player t, String reason, String time) {
        FileConfiguration c = DataManager.config;
        long sqltime = 0;
        if(!time.toLowerCase(Locale.ROOT).equals("perm")) {
            sqltime = System.currentTimeMillis()+toMilliSec(time);
        }
        String uuid = t.getUniqueId().toString();
        if(c.getString("storage-type").equals("mysql")) {
            if (SQLManager.connect()) {
                if (SQLManager.isConnected()) {
                    Connection connection = SQLManager.getConnection();
                    try {
                        String sqlinsert = "INSERT INTO data (uuid, reason, time) VALUES (?, ?, ?)";
                        PreparedStatement stmtinsert = connection.prepareStatement(sqlinsert);
                        stmtinsert.setString(1, uuid);
                        stmtinsert.setString(2, reason);
                        stmtinsert.setLong(3, sqltime);
                        stmtinsert.executeUpdate();
                        t.kickPlayer(StringManager.color("&cYou have been banned for " + time + "!\n\n&cReason: "+reason));
                        p.sendMessage(StringManager.color("&a"+t.getName()+" has been banned for "+ time + " for the reason &f" + reason));
                    } catch (Exception e) {
                        e.printStackTrace();
                        StringManager.log(Level.WARNING, "&cCould not connect to the Database!");
                    }
                }
            }
        } else {
            Connection con = SQLiteManager.getConnection();
            try {
                PreparedStatement ps = con.prepareStatement("INSERT INTO data (uuid, reason, time) VALUES (?, ?, ?)");
                ps.setString(1, uuid);
                ps.setString(2, reason);
                ps.setLong(3, sqltime);
                ps.executeUpdate();
                t.kickPlayer(StringManager.color("&cYou have been banned for " + time + "!\n\n&cReason: "+reason));
                p.sendMessage(StringManager.color("&a"+t.getName()+" has been banned for "+ time + " for the reason &f" + reason));
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
