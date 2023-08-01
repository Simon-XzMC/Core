package me.simonxz.core;

import me.simonxz.core.api.EnchantsManager;
import me.simonxz.core.commands.PickaxeCommand;
import me.simonxz.core.commands.SkinCommand;
import me.simonxz.core.gui.*;
import me.simonxz.core.pickaxes.PickaxeInteract;
import me.simonxz.core.utils.CreatePickaxe;
import me.simonxz.playermanager.PlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {

    public Map<String, String> enchantmentList = new HashMap<>();

    private EnchantsManager eManager;

    private PlayerManager playerManager;

    public PlayerManager getManager() {
        return playerManager;
    }


    @Override
    public void onEnable() {
        playerManager = (PlayerManager) Bukkit.getPluginManager().getPlugin("PlayerManager");
        this.eManager = new EnchantsManager();
        getConfig().options().copyDefaults(true);
        saveConfig();
        registerCommands();
        registerListeners();
        loadEnchantments();
        // Plugin startup logic

    }

    @Override
    public void onDisable() {}

    public void registerCommands() {
        getCommand("pick").setExecutor((CommandExecutor)new PickaxeCommand());
        getCommand("skinset").setExecutor((CommandExecutor)new SkinCommand());
    }


    public void registerListeners() {
        getServer().getPluginManager().registerEvents((Listener)new CreatePickaxe(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PickaxeInteract(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PickaxeMenu(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new EnchantMenu(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new PrestigeMenu(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new EnchantUpgradeMenu(), (Plugin)this);
        getServer().getPluginManager().registerEvents((Listener)new CloseInventory(), (Plugin)this);

    }

    public EnchantsManager getEnchantManager() {
        return this.eManager;
    }

    public void loadEnchantments() {
        Object[] enchantsFromConfig = getConfig().getConfigurationSection("Enchantments.Enchantments").getKeys(false).toArray();
        int enchantmentCount = 0;
        byte b;
        int i;
        Object[] arrayOfObject1;
        for (i = (arrayOfObject1 = enchantsFromConfig).length, b = 0; b < i; ) {
            Object o = arrayOfObject1[b];
            String rarity = getConfig().getString("Enchantments.Enchantments." + o.toString() + ".Rarity");
            this.enchantmentList.put(o.toString(), rarity);
            this.eManager.loadEnchant(o.toString());
            enchantmentCount++;
            b++;
        }
        Bukkit.getConsoleSender().sendMessage(String.valueOf(enchantmentCount) + " enchantments successfully registered.");
    }

}