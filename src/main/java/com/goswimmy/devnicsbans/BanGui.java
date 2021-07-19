package com.goswimmy.devnicsbans;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class BanGui {

    public static void openGui(Player p, Player t) {
        Inventory inv = Bukkit.createInventory(null, 27, StringManager.color("&0Ban - "+t.getName()));

        FileConfiguration c = DataManager.config;

        for(String key : c.getConfigurationSection("reasons").getKeys(false)) {
            ItemStack i = new ItemStack(Material.OAK_SIGN);
            ItemMeta m = i.getItemMeta();
            m.setDisplayName(StringManager.color(c.getString("reasons."+key+".name")));
            m.setLore(Arrays.asList(StringManager.color("&eTime: "+c.getString("reasons."+key+".time"))));
            i.setItemMeta(m);
            inv.addItem(i);
        }

        p.openInventory(inv);
    }

}
