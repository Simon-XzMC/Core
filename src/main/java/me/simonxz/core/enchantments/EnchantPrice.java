package me.simonxz.core.enchantments;

import me.simonxz.core.Main;
import me.simonxz.core.api.Enchants;
import org.bukkit.inventory.ItemStack;

public class EnchantPrice {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    EAPI api = new EAPI();

    public long getPrice(ItemStack item, String enchantment, int levelsToAdd) {
        Enchants e = plugin.getEnchantManager().getEnchant(enchantment);
        int currentlevel = 1 + this.api.getNBT(item, enchantment);
        long currentCost = 0L;
        for (int i = 0; i < levelsToAdd; i++) {
            currentlevel++;
            long extra = (long)(e.getPrice() + currentlevel * e.getPrice() * e.getExponent());
            currentCost += extra;
        }
        return currentCost;
    }
}
