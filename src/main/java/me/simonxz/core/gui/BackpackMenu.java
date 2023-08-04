package me.simonxz.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;

public class BackpackMenu implements Listener {

    public void BackpackMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, "Backpack Upgrade Menu");
        p.openInventory(inv);
    }


}
