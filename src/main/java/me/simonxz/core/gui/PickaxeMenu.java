package me.simonxz.core.gui;

import me.simonxz.core.Main;
import me.simonxz.core.utils.Utils;
import me.simonxz.playermanager.users.User;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

public class PickaxeMenu implements Listener {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    EnchantMenu enchantMenu = new EnchantMenu();

    PrestigeMenu prestigeMenu = new PrestigeMenu();

    public void pickaxeHub(final Player p) {
        final Inventory inv = Bukkit.createInventory(null, 54, "Pickaxe Menu");
        User user = plugin.getManager().getPlayerManager().getUser(p.getUniqueId());
        long tokens = user.getTokens();
        final int[] border = {
                0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
                17, 18, 26, 27, 35, 36, 44, 45, 46, 47,
                48, 49, 50, 51, 52, 53 };
        for (int i = 0; i < 54; i++) {
            double chance = Utils.randomDouble(0.0D, 100.0D);
            ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1);
            if (chance > 50.0D)
                item.setType(Material.GRAY_STAINED_GLASS_PANE);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(" ");
            meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
            item.setItemMeta(meta);
            inv.setItem(i, item);
        }
        (new BukkitRunnable() {
            public void run() {
                if (!p.getOpenInventory().getTitle().equals("Pickaxe Menu"))
                    cancel();
                byte b;
                int i;
                int[] arrayOfInt;
                for (i = (arrayOfInt = border).length, b = 0; b < i; ) {
                    int slot = arrayOfInt[b];
                    double chance = Utils.randomDouble(0.0D, 100.0D);
                    ItemStack item = new ItemStack(Material.CYAN_STAINED_GLASS_PANE, 1);
                    ItemMeta meta = item.getItemMeta();
                    meta.setDisplayName(" ");
                    meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS });
                    item.setItemMeta(meta);
                    if (chance > 50.0D)
                        item.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
                    inv.setItem(slot, item);
                    b++;
                }
            }
        }).runTaskTimer((Plugin)plugin, 0L, 10L);
        inv.setItem(40, Utils.makeItem(Material.ORANGE_STAINED_GLASS_PANE, Utils.color("&6&l" + Utils.format(tokens) + " Token(s)"), new String[0]));
        inv.setItem(21, Utils.makeItem(Material.TNT_MINECART, Utils.color("&6&lENCHANTMENTS"), new String[] { Utils.color(" "),
                Utils.color("&7Upgrade your enchantments with "),
                Utils.color("&7tokens or crystals. "),
                Utils.color(" ") }));
        inv.setItem(29, Utils.makeItem(Material.CHEST, Utils.color("&b&lBACKPACK"), new String[] { Utils.color(" "),
                Utils.color("&7View your backpack and purchase "),
                Utils.color("&7upgrades here. "),
                Utils.color(" ") }));
        inv.setItem(23, Utils.makeItem(Material.NETHER_STAR, Utils.color("&4&lPRESTIGE"), new String[] { Utils.color(" "),
                Utils.color("&7Level up and prestige your pickaxe "),
                Utils.color("&7to increase its power and unlock "),
                Utils.color("&7new enchantments. "),
                Utils.color(" ") }));
        inv.setItem(33, Utils.makeItem(Material.ENCHANTED_BOOK, Utils.color("&a&lMINE PASS"), new String[] { Utils.color(" "),
                Utils.color("&7Level up your mine pass to unlock "),
                Utils.color("&7great rewards and loot. "),
                Utils.color(" ") }));
        inv.setItem(13, p.getInventory().getItemInMainHand());
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2.0F, 2.0F);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Pickaxe Menu")) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR)
                return;
            Player p = (Player)e.getWhoClicked();
            if (clickedItem.getItemMeta().getDisplayName().contains("ENCHANTMENTS")) {
                this.enchantMenu.enchantMenu(p);
            } else if (clickedItem.getItemMeta().getDisplayName().contains("PRESTIGE")) {
                this.prestigeMenu.pickaxePrestige(p);
            }
        }
    }
}