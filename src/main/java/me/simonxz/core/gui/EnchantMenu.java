package me.simonxz.core.gui;


import me.simonxz.core.Main;
import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.EnchantMax;
import me.simonxz.core.enchantments.EnchantPrice;
import me.simonxz.core.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EnchantMenu implements Listener {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    EnchantPrice cost = new EnchantPrice();

    EAPI api = new EAPI();

    EnchantUpgradeMenu eum = new EnchantUpgradeMenu();

    private ConfigurationSection config = plugin.getConfig().getConfigurationSection("Enchantments.Enchantments");

    public void enchantMenu(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, "Enchantment Menu");
        ItemStack pick = p.getInventory().getItemInMainHand();
        for (int i = 0; i < 45; ) {
            inv.setItem(i, Utils.makeItem(Material.BLACK_STAINED_GLASS_PANE, Utils.color("&r"), new String[0]));
            i++;
        }
        for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
            String name = ench.getKey();
            int display_slot = this.config.getInt(String.valueOf(name) + ".GUI_Slot");
            Material display_item = Material.valueOf(this.config.getString(String.valueOf(name) + ".GUI_Item"));
            String display_name = this.config.getString(String.valueOf(name) + ".GUI_Name");
            long price = this.cost.getPrice(pick, name, 1);
            int max = plugin.getEnchantManager().getEnchant(name).getMax();
            int requirement = plugin.getEnchantManager().getEnchant(name).getRequirement();
            int pickPrestige = this.api.getNBT(pick, "Prestige");
            List<String> desc = this.config.getStringList(String.valueOf(name) + ".GUI_Lore");
            ItemStack item = new ItemStack(display_item);
            ItemMeta meta = item.getItemMeta();
            meta.setDisplayName(Utils.color(display_name));
            ArrayList<String> lore = new ArrayList<>();
            List<String> getLore = plugin.getConfig().getStringList("Enchantments.GUI_Upgrade_Lore");
            for (String a : getLore) {
                if (a.contains("{requirement}")) {
                    if (pickPrestige < requirement) {
                        lore.add(Utils.color(" "));
                        lore.add(Utils.color("&cUnlock this enchantment at Pickaxe Prestige " + requirement));
                        continue;
                    }
                    lore.add(Utils.color(" "));
                    continue;
                }
                if (a.contains("{description}")) {
                    for (String b : desc)
                        lore.add(Utils.color(b));
                    continue;
                }
                lore.add(Utils.color(a).replace("{price}", Utils.format(price)).replace("{maxlevel}", Utils.format(max))
                        .replace("{current}", Utils.insertCommas(this.api.getNBT(pick, name))).replace("{enchantment}", name.replaceAll("_", " ")));
            }
            meta.setLore(lore);
            item.setItemMeta(meta);
            inv.setItem(display_slot, item);
        }
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2.0F, 2.0F);
    }

    EnchantMax max = new EnchantMax();

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getView().getTitle().contains("Enchantment Menu")) {
            e.setCancelled(true);
            ItemStack clickedItem = e.getCurrentItem();
            if (clickedItem == null || clickedItem.getType() == Material.AIR)
                return;
            Player p = (Player)e.getWhoClicked();
            String enchant = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).replace(" ", "_");
            ItemStack item = p.getInventory().getItemInMainHand();
            if (plugin.getEnchantManager().getEnchant(enchant) != null) {
                if (this.api.getNBT(item, "Prestige") >= plugin.getEnchantManager().getEnchant(enchant).getRequirement()) {
                    if (e.getClick() == ClickType.DROP) {
                        this.max.enchantMax(p, enchant);
                        return;
                    }
                    this.eum.enchanter(p, enchant);
                    return;
                }
                p.sendMessage(Utils.color("&cYou do not have the required prestige for " + enchant.replace("_", " ")));
            }
        }
    }
}