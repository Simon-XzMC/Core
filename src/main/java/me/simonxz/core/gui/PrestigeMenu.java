package me.simonxz.core.gui;


import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.pickaxes.Prestige;
import me.simonxz.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PrestigeMenu implements Listener {
    EAPI api = new EAPI();

    Prestige prestige = new Prestige();

    public void pickaxePrestige(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, "Pickaxe Prestige Menu");
        ItemStack item = p.getInventory().getItemInMainHand();
        int level = this.api.getNBT(item, "Level");
        double per = ((level - 1) * 100 / 49);
        for (int i = 0; i < 45; ) {
            inv.setItem(i, Utils.makeItem(Material.BLACK_STAINED_GLASS_PANE, Utils.color("&r"), new String[0]));
            i++;
        }
        inv.setItem(22, Utils.makeItem(Material.ENCHANTED_BOOK, Utils.color("&c&lPICKAXE PRESTIGE"), new String[] {
                Utils.color(" "),
                Utils.color("&7Click here to prestige your pickaxe "),
                Utils.color("&7and reset the level."),
                Utils.color(" "),
                Utils.color("&e&lPROGRESS "),
                Utils.color(this.api.newProgressBar(per)),
                Utils.color(" "),
                Utils.color("&a&lREWARDS ( &fSelectable&a&l ) "),
                Utils.color("&a+&f0.02x &8to &a+&f0.05x &eFortune Multiplier "),
                Utils.color("&a+&f0.01x &8to &a+&f0.03x &6Proc Multiplier "),
                Utils.color("&a+&f0.03x &8to &a+&f0.07x &aLucky Multiplier "),
                Utils.color(" "),
                Utils.color("&7&oSelect your Pickaxe Multiplier on the next "),
                Utils.color("&7&opage to finalize the prestige. "),
                Utils.color(" ") }));
        inv.setItem(44, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, Utils.color("&c&lCANCEL"), new String[] { Utils.color("&cReturn to Pickaxe Menu. &r") }));
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2.0F, 2.0F);
    }

    public void confirmPrestige(Player p) {
        Inventory inv = Bukkit.createInventory(null, 27, "Confirm Pickaxe Prestige");
        for (int i = 0; i < 27; ) {
            inv.setItem(i, Utils.makeItem(Material.BLACK_STAINED_GLASS_PANE, Utils.color("&r"), new String[0]));
            i++;
        }
        inv.setItem(10, Utils.makeItem(Material.HONEYCOMB, Utils.color("&e&l+FORTUNE MULTIPLIER"), new String[] { Utils.color(" "),
                Utils.color("&f&lClick Here&7 to confirm your &c&lPrestige&7 and "),
                Utils.color("&7choose &eFortune Multiplier&7 as your reward. "),
                Utils.color(" "),
                Utils.color("&c&l&oWarning: &7&oThis will finalize your prestige "),
                Utils.color("&7&oand cannot be undone! "),
                Utils.color(" ") }));
        inv.setItem(13, Utils.makeItem(Material.HONEYCOMB, Utils.color("&6&l+PROC MULTIPLIER"), new String[] { Utils.color(" "),
                Utils.color("&f&lClick Here&7 to confirm your &c&lPrestige&7 and "),
                Utils.color("&7choose &6Proc Multiplier&7 as your reward. "),
                Utils.color(" "),
                Utils.color("&c&l&oWarning: &7&oThis will finalize your prestige "),
                Utils.color("&7&oand cannot be undone! "),
                Utils.color(" ") }));
        inv.setItem(16, Utils.makeItem(Material.HONEYCOMB, Utils.color("&a&l+LUCKY MULTIPLIER"), new String[] { Utils.color(" "),
                Utils.color("&f&lClick Here&7 to confirm your &c&lPrestige&7 and "),
                Utils.color("&7choose &aLucky Multiplier&7 as your reward. "),
                Utils.color(" "),
                Utils.color("&c&l&oWarning: &7&oThis will finalize your prestige "),
                Utils.color("&7&oand cannot be undone! "),
                Utils.color(" ") }));
        inv.setItem(26, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, Utils.color("&c&lCANCEL"), new String[] { Utils.color("&cReturn to Pickaxe Prestige Menu. &r") }));
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2.0F, 2.0F);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Pickaxe Prestige Menu")) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR)
                return;
            Player p = (Player)e.getWhoClicked();
            if (clickedItem.getItemMeta().getDisplayName().contains("PICKAXE PRESTIGE")) {
                if (this.api.getNBT(p.getInventory().getItemInMainHand(), "Level") >= 50) {
                    confirmPrestige(p);
                } else {
                    p.sendMessage(Utils.color("&eYou must have a &fLevel 50 Pickaxe&e to prestige."));
                }
            } else if (clickedItem.getItemMeta().getDisplayName().contains("CANCEL")) {
                p.closeInventory();
            }
        } else if (e.getView().getTitle().contains("Confirm Pickaxe Prestige")) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR)
                return;
            Player p = (Player)e.getWhoClicked();
            ItemStack item = p.getInventory().getItemInMainHand();
            if (clickedItem.getItemMeta().getDisplayName().contains("+FORTUNE MULTIPLIER")) {
                this.prestige.prestigePickaxe(p, item, "Fortune_Multiplier");
                p.closeInventory();
            } else if (clickedItem.getItemMeta().getDisplayName().contains("+PROC MULTIPLIER")) {
                this.prestige.prestigePickaxe(p, item, "Proc_Multiplier");
                p.closeInventory();
            } else if (clickedItem.getItemMeta().getDisplayName().contains("+LUCKY MULTIPLIER")) {
                this.prestige.prestigePickaxe(p, item, "Lucky_Multiplier");
                p.closeInventory();
            } else if (clickedItem.getItemMeta().getDisplayName().contains("CANCEL")) {
                p.closeInventory();
            }
        }
    }
}
