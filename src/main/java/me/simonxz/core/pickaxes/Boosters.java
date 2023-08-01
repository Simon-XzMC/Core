package me.simonxz.core.pickaxes;

import java.util.ArrayList;

import me.simonxz.core.Main;
import me.simonxz.core.utils.Utils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
public class Boosters implements Listener {

    private static Main plugin = Main.getPlugin(Main.class);

    public ItemStack givePickaxeBooster(Player p, String type, double min, double max) {

        if(type.equalsIgnoreCase("fortune")||type.equalsIgnoreCase("proc")||type.equalsIgnoreCase("lucky")) {

            ArrayList<String> lore = new ArrayList<String>();

            ItemStack item = new ItemStack(Material.PAPER);
            ItemMeta meta = item.getItemMeta();
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&4&l �?� Omnitool Booster &r"));
            lore.add(Utils.color(" "));
            lore.add(Utils.color("&cA permanent booster applicable to &r"));
            lore.add(Utils.color("&cyour Omnitool &r"));
            lore.add(Utils.color(" "));
            lore.add(Utils.color("&8&l ► &7Type &f" + Utils.capitalize(type)));
            lore.add(Utils.color("&8&l ► &7Range &f" + min + " &7-&f " + max));
            lore.add(Utils.color(" "));
            item.addUnsafeEnchantment(Enchantment.DIG_SPEED, 999);
            meta.setCustomModelData(10);
            meta.setLore(lore);

            // NBT Values
            NamespacedKey ntype = new NamespacedKey(plugin, "type");
            meta.getPersistentDataContainer().set(ntype, PersistentDataType.STRING, Utils.capitalize(type));
            NamespacedKey nmin = new NamespacedKey(plugin, "min");
            meta.getPersistentDataContainer().set(nmin, PersistentDataType.DOUBLE, min);
            NamespacedKey nmax = new NamespacedKey(plugin, "max");
            meta.getPersistentDataContainer().set(nmax, PersistentDataType.DOUBLE, max);

            // Update
            item.setItemMeta(meta);

            return item;

        }
        return null;

    }


}

