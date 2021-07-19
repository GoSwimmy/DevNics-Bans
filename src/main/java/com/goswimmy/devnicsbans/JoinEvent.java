package com.goswimmy.devnicsbans;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class JoinEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        if(BanManager.isBanned(p)) {
            String reason = BanManager.getReason(p);
            e.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringManager.color("&cYou have been banned!\n\n&cReason: "+reason));
        }
    }
}