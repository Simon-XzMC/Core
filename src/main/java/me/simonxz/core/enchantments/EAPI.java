package me.simonxz.core.enchantments;

import me.simonxz.core.Main;
import me.simonxz.core.utils.Utils;
import java.text.DecimalFormat;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

public class EAPI {

    private static Main plugin = (Main) Main.getPlugin(Main.class);


    public void setNBT(ItemStack item, String enchantment, int level) {
        if (item != null && enchantment != null && level >= 0) {
            NamespacedKey key = new NamespacedKey((Plugin)plugin, enchantment);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, Integer.valueOf(level));
            itemMeta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ATTRIBUTES});
            item.setItemMeta(itemMeta);
        }
    }

    public int getNBT(ItemStack item, String enchantment) {
        NamespacedKey key = new NamespacedKey((Plugin)plugin, enchantment);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return 0;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.INTEGER))
            return ((Integer)container.get(key, PersistentDataType.INTEGER)).intValue();
        return 0;
    }

    public void setNBTString(ItemStack item, String enchantment, String val) {
        if (item != null && enchantment != null && val != null) {
            NamespacedKey key = new NamespacedKey((Plugin)plugin, enchantment);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.STRING, val);
            itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
            item.setItemMeta(itemMeta);
        }
    }

    public String getNBTString(ItemStack item, String a) {
        NamespacedKey key = new NamespacedKey((Plugin)plugin, a);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return null;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING))
            return (String)container.get(key, PersistentDataType.STRING);
        return null;
    }

    public boolean enchantEnabled(ItemStack item, String enchantment) {
        enchantment = String.valueOf(enchantment) + "_Toggled";
        NamespacedKey key = new NamespacedKey((Plugin)plugin, enchantment);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return false;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING))
            return true;
        return false;
    }

    public double getNBTDouble(ItemStack item, String a) {
        NamespacedKey key = new NamespacedKey((Plugin)plugin, a);
        ItemMeta itemMeta = item.getItemMeta();
        if (itemMeta == null)
            return 0.0D;
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.DOUBLE))
            return ((Double)container.get(key, PersistentDataType.DOUBLE)).doubleValue();
        return 0.0D;
    }

    public void setNBTDouble(ItemStack item, String enchantment, Double val) {
        if (item != null && enchantment != null && val.doubleValue() >= 0.0D) {
            NamespacedKey key = new NamespacedKey((Plugin)plugin, enchantment);
            ItemMeta itemMeta = item.getItemMeta();
            itemMeta.getPersistentDataContainer().set(key, PersistentDataType.DOUBLE, val);
            itemMeta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ATTRIBUTES });
            item.setItemMeta(itemMeta);
        }
    }

    public String newProgressBar(double d) {
        DecimalFormat df = new DecimalFormat("####0.00");
        String s = null;
        double i = d;
        if (i == 100.0D) {
            s = "&a&l&f(100%)";
        } else if (i >= 90.0D && i < 100.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 80.0D && i < 90.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 70.0D && i < 80.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 60.0D && i < 70.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 50.0D && i < 60.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 40.0D && i < 50.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 30.0D && i < 40.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 20.0D && i < 30.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 10.0D && i < 20.0D) {
            s = "&a&l&f(" + df.format(d) + "%)";
        } else if (i >= 0.0D && i < 10.0D) {
            s = "&7&l&f(" + df.format(d) + "%)";
        }
        return s;
    }

}
