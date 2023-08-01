package me.simonxz.core.pickaxes;

import me.simonxz.core.Main;
import me.simonxz.core.api.Enchants;
import me.simonxz.core.api.EnchantsManager;
import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.PickaxeUpdater;
import me.simonxz.core.gui.PickaxeMenu;
import me.simonxz.playermanager.Utils;
import me.simonxz.playermanager.users.User;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;


import java.util.*;

public class PickaxeInteract implements Listener {
    EAPI api = new EAPI();

    PickaxeMenu pm = new PickaxeMenu();

    PickaxeUpdater update = new PickaxeUpdater();

    private EnchantsManager eManager;


    public Map<UUID, Long> hubCooldown = new HashMap<>();

    private static Main plugin = (Main) Main.getPlugin(Main.class);


    @EventHandler
    public void rightClickPickaxe(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        Action action = e.getAction();
        if (this.api.getNBT(p.getInventory().getItemInMainHand(), "ValidationCheck") == 1 && (
                action.equals(Action.RIGHT_CLICK_AIR) || action.equals(Action.RIGHT_CLICK_BLOCK))) {
            Long cooldown = this.hubCooldown.get(p.getUniqueId());
            if (cooldown != null && cooldown.longValue() > System.currentTimeMillis())
                return;
            this.hubCooldown.put(p.getUniqueId(), Long.valueOf(System.currentTimeMillis() + 1000L));
            this.pm.pickaxeHub(p);
        }
    }

    public Map<Player, Long> pickaxeCooldown = new HashMap<Player, Long>();




    @EventHandler
    public void BlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        ItemStack item = p.getInventory().getItemInMainHand();
        api.setNBT(item, "Blocks", api.getNBT(item, "Blocks")+1);
        api.setNBT(item, "XP", api.getNBT(item, "XP")+50);

        for (ItemStack is : e.getBlock().getDrops())
            e.getPlayer().getInventory().addItem(is);
        e.getBlock().getDrops().clear();

        if (p.getCanPickupItems() == false) {
            p.sendTitle("§cInventory is Full!", "§7Sell items in your inventory to collect money!", 1 ,20, 1);
            e.setCancelled(true);
        }

        if(pickaxeCooldown.get(p) != null && pickaxeCooldown.get(p) > System.currentTimeMillis()) return;
        update.updatePickaxe(item);

        User user = plugin.getManager().getPlayerManager().getUser(p.getUniqueId());

        Enchants enchant = plugin.getEnchantManager().getEnchant(String.valueOf(item));
        user.addTokens(100);
    }


    public static double randomDouble(double ranMin, double ranMax) {
        Random r = new Random();
        double randomValue = ranMin + (ranMax - ranMin) * r.nextDouble();
        if(randomValue>0) return randomValue;
        return 0;
    }




    @EventHandler
    public void Explosive(BlockBreakEvent e) {
        EnchantsManager enchants = plugin.getEnchantManager();
        Player p = e.getPlayer();
        ItemStack pickaxe = p.getInventory().getItemInMainHand();
        int level = api.getNBT(pickaxe, "Explosive");
        Enchants enchant = enchants.getEnchant("Explosive");
        if (enchant == null) return;
        if (level>0) {
         if (randomDouble(0, 100) <= level * enchant.getProc()) {
             for (ItemStack is : e.getBlock().getDrops())
                 e.getPlayer().getInventory().addItem(is);
             e.getBlock().getDrops().clear();
             if (p.getCanPickupItems() == false) {
                 p.sendTitle("§cInventory is Full!", "§7Sell items in your inventory to collect money!", 1 ,20, 1);
                 e.setCancelled(true);
             }
                World world = e.getBlock().getWorld();
                Location location = e.getBlock().getLocation();
                world.createExplosion(location, 10f);
            }

        }
    }
}

