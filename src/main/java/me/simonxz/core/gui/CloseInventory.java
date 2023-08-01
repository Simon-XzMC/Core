package me.simonxz.core.gui;

import me.simonxz.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.plugin.Plugin;

public class CloseInventory implements Listener {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    PickaxeMenu main = new PickaxeMenu();

    @EventHandler
    public void inventoryClose(InventoryCloseEvent e) {
        final Player player = (Player)e.getPlayer();
        if (e.getView().getTitle().contains("Enchantment Menu") || e.getView().getTitle().contains("Pickaxe Prestige Menu"))
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)plugin, new Runnable() {
                public void run() {
                    if (player.getOpenInventory().getTopInventory().isEmpty())
                        CloseInventory.this.main.pickaxeHub(player);
                }
            },  3L);
        if (e.getView().getTitle().contains("Enchanter"))
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)plugin, new Runnable() {
                public void run() {
                    if (player.getOpenInventory().getTopInventory().isEmpty())
                        CloseInventory.this.main.enchantMenu.enchantMenu(player);
                }
            },  3L);
        if (e.getView().getTitle().contains("Confirm Pickaxe Prestige"))
            Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin)plugin, new Runnable() {
                public void run() {
                    if (player.getOpenInventory().getTopInventory().isEmpty())
                        CloseInventory.this.main.prestigeMenu.pickaxePrestige(player);
                }
            },  3L);
    }
}
