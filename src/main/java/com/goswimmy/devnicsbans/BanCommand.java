package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BanCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                Player t = Bukkit.getPlayer(args[0]);
                if(t == null) {
                    p.sendMessage(StringManager.color("&cThat player is not online!"));
                } else {
                    BanGui.openGui(p, t);
                }
            }
        }
        return false;
    }

}
