package me.simonxz.core.commands;

import me.simonxz.core.Main;
import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.PickaxeUpdater;
import me.simonxz.core.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SkinCommand implements CommandExecutor {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    EAPI api = new EAPI();

    PickaxeUpdater pu = new PickaxeUpdater();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("skinset")) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                if (args.length != 1) {
                    p.sendMessage("Enter the skin you wish to apply.");
                    return true;
                }
                String skin = args[0];
                if (plugin.getConfig().getConfigurationSection("Color_Packs." + skin) != null) {
                    ItemStack pick = p.getInventory().getItemInMainHand();
                    if (this.api.getNBT(pick, "ValidationCheck") > 0) {
                        ItemMeta meta = pick.getItemMeta();
                        meta.setDisplayName(Utils.color(String.valueOf(plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("1")) + "&lPickaxe"));
                        pick.setItemMeta(meta);
                        this.api.setNBTString(pick, "Color_1", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("1"));
                        this.api.setNBTString(pick, "Color_2", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("2"));
                        this.api.setNBTString(pick, "Color_3", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("3"));
                        this.api.setNBTString(pick, "Color_4", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("4"));
                        this.api.setNBTString(pick, "Color_5", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("5"));
                        this.api.setNBTString(pick, "Color_6", plugin.getConfig().getConfigurationSection("Color_Packs." + skin).getString("6"));
                        this.api.setNBTString(pick, "Skin", skin);
                        this.pu.updatePickaxe(pick);
                        p.sendMessage("Skin successfully applied.");
                    } else {
                        p.sendMessage("Invalid item.");
                    }
                } else {
                    p.sendMessage("Invalid pickaxe skin.");
                }
            }
            return true;
        }
        return false;
    }
}


