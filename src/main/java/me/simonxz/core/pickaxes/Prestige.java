package me.simonxz.core.pickaxes;


import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.PickaxeUpdater;
import me.simonxz.core.utils.Utils;
import java.text.DecimalFormat;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
public class Prestige {
    EAPI api = new EAPI();

    PickaxeUpdater update = new PickaxeUpdater();

    DecimalFormat df = new DecimalFormat("####0.00");

    public void prestigePickaxe(Player p, ItemStack item, String type) {
        int level = this.api.getNBT(item, "Level");
        int prestige = this.api.getNBT(item, "Prestige");
        double booster = this.api.getNBTDouble(item, type);
        if (level >= 50) {
            this.api.setNBT(item, "Level", 1);
            this.api.setNBT(item, "XP", 0);
            this.api.setNBT(item, "Prestige", prestige + 1);
            double reward = 0.0D;
            if (type.equalsIgnoreCase("Fortune_Multiplier")) {
                reward = Utils.randomDouble(0.02D, 0.05D);
            } else if (type.equalsIgnoreCase("Proc_Multiplier")) {
                reward = Utils.randomDouble(0.01D, 0.03D);
            } else if (type.equalsIgnoreCase("Lucky_Multiplier")) {
                reward = Utils.randomDouble(0.03D, 0.07D);
            }
            String rewardString = "&c&lPickaxe Multiplier &7(&a&l+&f" + this.df.format(reward) + "x " + type.replace("_", " ") + "&7)";
            this.api.setNBTDouble(item, type, Double.valueOf(booster + reward));
            this.update.updatePickaxe(item);
            p.sendMessage(Utils.color("&aYou prestiged your pickaxe from " + Utils.toRoman(prestige) + " to " + Utils.toRoman(prestige + 1) + "!"));
            p.sendMessage(Utils.color("&eYou received " + rewardString + "&e."));
            p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 1.0F, 6.0F);
        } else {
            p.sendMessage(Utils.color("&cYour pickaxe isn't ready to be prestiged."));
        }
    }
}