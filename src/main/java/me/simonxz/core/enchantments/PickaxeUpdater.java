package me.simonxz.core.enchantments;

import me.simonxz.core.Main;
import me.simonxz.core.utils.Utils;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Map;

public class PickaxeUpdater {

    private static Main plugin = (Main) Main.getPlugin(Main.class);

    EAPI api = new EAPI();



    public void updatePickaxe(ItemStack item) {
        if (item != null) {
            ArrayList<String> lore = new ArrayList<>();
            ItemMeta item_meta = item.getItemMeta();
            DecimalFormat df = new DecimalFormat("####0.00");
            int level = this.api.getNBT(item, "Level");
            int prestige = this.api.getNBT(item, "Prestige");
            int xp = this.api.getNBT(item, "XP");
            int blocks = this.api.getNBT(item, "Blocks");
            String skin = this.api.getNBTString(item, "Skin");
            String color_1 = this.api.getNBTString(item, "Color_1");
            String color_2 = this.api.getNBTString(item, "Color_2");
            String color_3 = this.api.getNBTString(item, "Color_3");
            String color_4 = this.api.getNBTString(item, "Color_4");
            String color_5 = this.api.getNBTString(item, "Color_5");
            String color_6 = this.api.getNBTString(item, "Color_6");
            Long needxp = (long) Math.round((500 * level));
            double per = (xp * 100) / needxp;
            if (xp >= needxp.longValue()) {
                NamespacedKey nbt_level = new NamespacedKey((Plugin)plugin, "Level");
                item_meta.getPersistentDataContainer().set(nbt_level, PersistentDataType.INTEGER, Integer.valueOf(level + 1));
                NamespacedKey nbt_xp = new NamespacedKey((Plugin)plugin, "XP");
                item_meta.getPersistentDataContainer().set(nbt_xp, PersistentDataType.INTEGER, Integer.valueOf(0));
                per = 0.0D;
                level++;
            }
            lore.add(Utils.color(String.valueOf(color_2) + " Buy Tokens @ "));
            lore.add(Utils.color(String.valueOf(color_2) + " www.store.pepsiprison.com "));
            lore.add(Utils.color(" "));
            lore.add(Utils.color(String.valueOf(color_1) + "&lEnchantments"));
            for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
                if (((String)ench.getValue()).equalsIgnoreCase("Common") &&
                        this.api.getNBT(item, ench.getKey()) > 0 &&
                        !this.api.enchantEnabled(item, ench.getKey()))
                    lore.add(Utils.color(String.valueOf(color_3) + "&l | " + color_3 + ((String)ench.getKey()).replace("_", " ") + " " +
                            NumberFormat.getInstance().format(this.api.getNBT(item, ench.getKey()))));
            }
            for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
                if (((String)ench.getValue()).equalsIgnoreCase("Uncommon") &&
                        this.api.getNBT(item, ench.getKey()) > 0 &&
                        !this.api.enchantEnabled(item, ench.getKey()))
                    lore.add(Utils.color(String.valueOf(color_3) + "&l | " + color_3 + ((String)ench.getKey()).replace("_", " ") + " " +
                            NumberFormat.getInstance().format(this.api.getNBT(item, ench.getKey()))));
            }
            for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
                if (((String)ench.getValue()).equalsIgnoreCase("Rare") &&
                        this.api.getNBT(item, ench.getKey()) > 0 &&
                        !this.api.enchantEnabled(item, ench.getKey()))
                    lore.add(Utils.color(String.valueOf(color_4) + "&l | " + color_4 + ((String)ench.getKey()).replace("_", " ") + " " +
                            NumberFormat.getInstance().format(this.api.getNBT(item, ench.getKey()))));
            }
            for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
                if (((String)ench.getValue()).equalsIgnoreCase("Ultimate") &&
                        this.api.getNBT(item, ench.getKey()) > 0 &&
                        !this.api.enchantEnabled(item, ench.getKey()))
                    lore.add(Utils.color(String.valueOf(color_5) + "&l | " + color_5 + ((String)ench.getKey()).replace("_", " ") + " " +
                            NumberFormat.getInstance().format(this.api.getNBT(item, ench.getKey()))));
            }
            for (Map.Entry<String, String> ench : (Iterable<Map.Entry<String, String>>)plugin.enchantmentList.entrySet()) {
                if (((String)ench.getValue()).equalsIgnoreCase("Divine") &&
                        this.api.getNBT(item, ench.getKey()) > 0 &&
                        !this.api.enchantEnabled(item, ench.getKey()))
                    lore.add(Utils.color(String.valueOf(color_6) + "&l | " + color_6 + ((String)ench.getKey()).replace("_", " ") + " " +
                            NumberFormat.getInstance().format(this.api.getNBT(item, ench.getKey()))));
            }
            lore.add(Utils.color(" "));
            lore.add(Utils.color(String.valueOf(color_1) + "&lStatistics"));
            lore.add(Utils.color(String.valueOf(color_2) + "&l | " + color_2 + "Level: &f" + level + color_2 + " (" + df.format(per) + "%)"));
            lore.add(Utils.color(String.valueOf(color_2) + "&l | " + color_2 + "Prestige: &f" + Utils.toRoman(prestige)));
            lore.add(Utils.color(String.valueOf(color_2) + "&l | " + color_2 + "Mined: &f" + NumberFormat.getInstance().format(blocks)));
            lore.add(Utils.color(String.valueOf(color_2) + "&l | " + color_2 + "Skin: &f" + skin));
            lore.add(Utils.color(" "));
            item_meta.setLore(lore);
            item.setItemMeta(item_meta);
        }
    }
}

