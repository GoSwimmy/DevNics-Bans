package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CheckBanCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if(args.length == 1) {
                OfflinePlayer ot = Bukkit.getOfflinePlayer(args[0]);
                boolean isBanned = BanManager.isBanned(ot);
                if(isBanned) {
                    p.sendMessage(StringManager.color("&a"+ot.getName()+ " is banned!"));
                } else {
                    p.sendMessage(StringManager.color("&a"+ot.getName()+ " is not banned!"));
                }
            }
        }
        return false;
    }
}
