package me.simonxz.core.utils;

import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.PickaxeUpdater;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class CreatePickaxe implements Listener {
    EAPI ench = new EAPI();


    PickaxeUpdater pu = new PickaxeUpdater();


    public void defaultPickaxe(Player p) {
        ItemStack pickaxe = new ItemStack(Material.NETHERITE_PICKAXE);
        ItemMeta meta = pickaxe.getItemMeta();
        meta.addItemFlags(new ItemFlag[] { ItemFlag.HIDE_ENCHANTS});
        meta.addItemFlags(new ItemFlag[]{ ItemFlag.HIDE_UNBREAKABLE});
        meta.setDisplayName(Utils.color("&#0e4c92&lPickaxe"));
        meta.setUnbreakable(true);
        pickaxe.setItemMeta(meta);
        this.ench.setNBT(pickaxe, "ValidationCheck", 1);
        this.ench.setNBT(pickaxe, "Level", 1);
        this.ench.setNBT(pickaxe, "Prestige", 0);
        this.ench.setNBT(pickaxe, "XP", 0);
        this.ench.setNBT(pickaxe, "Blocks", 0);
        this.ench.setNBT(pickaxe, "Fortune", 10);
        this.ench.setNBT(pickaxe, "Key_Finder", 10);
        this.ench.setNBT(pickaxe, "Explosive", 10);
        this.ench.setNBTString(pickaxe, "Color_1", "&#6666FF");
        this.ench.setNBTString(pickaxe, "Color_2", "&#FF4C4C");
        this.ench.setNBTString(pickaxe, "Color_3", "&#0e78cf");
        this.ench.setNBTString(pickaxe, "Color_4", "&#4590de");
        this.ench.setNBTString(pickaxe, "Color_5", "&#5ADBFF");
        this.ench.setNBTString(pickaxe, "Color_6", "&#FFDD4A");
        this.pu.updatePickaxe(pickaxe);
        pickaxe.addUnsafeEnchantment(Enchantment.DIG_SPEED, 999);
        p.getInventory().addItem(new ItemStack[] { pickaxe });


    }
}
