package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryEvent implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if(ChatColor.stripColor(e.getView().getTitle()).contains("Ban - ")) {
            e.setCancelled(true);
            String playername = ChatColor.stripColor(e.getView().getTitle()).replace("Ban - ", "");
            Player t = Bukkit.getPlayer(playername);
            String time = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getLore().get(0)).replace("Time: ", "");
            if(t != null) {
                BanManager.banPlayer(p, t, e.getCurrentItem().getItemMeta().getDisplayName(), time);
            } else {
                p.closeInventory();
                p.sendMessage(StringManager.color("&cThat user is no longer online!"));
            }
        }
    }

}
