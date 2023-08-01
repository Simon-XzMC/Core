package me.simonxz.core.api;

import java.util.HashMap;
import java.util.Map;

import me.simonxz.core.Main;
import org.bukkit.configuration.ConfigurationSection;
public class EnchantsManager {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    private Map<String, Enchants> enchants = new HashMap<>();

    public Enchants getEnchant(String string) {
        for (Enchants enchant : this.enchants.values()) {
            if (enchant.getEnchant().equals(string))
                return enchant;
        }
        return null;
    }

    public void addEnhcant(Enchants e) {
        this.enchants.put(e.getEnchant(), e);
    }

    public void loadEnchant(String enchant) {
        ConfigurationSection config = plugin.getConfig().getConfigurationSection("Enchantments.Enchantments");
        Enchants b = new Enchants(enchant);
        b.setMax(config.getInt(String.valueOf(enchant) + ".Max"));
        b.setPrice(config.getLong(String.valueOf(enchant) + ".Price"));
        b.setExponent(config.getDouble(String.valueOf(enchant) + ".Exponent"));
        b.setRequirement(config.getInt(String.valueOf(enchant) + ".Requirement"));
        b.setProc(Double.valueOf(Double.parseDouble(config.getString(String.valueOf(enchant) + ".Proc"))));
    }
}
