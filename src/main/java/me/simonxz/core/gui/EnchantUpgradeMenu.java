package me.simonxz.core.gui;

import me.simonxz.core.Main;
import me.simonxz.core.api.Enchants;
import me.simonxz.core.enchantments.EAPI;
import me.simonxz.core.enchantments.EnchantPrice;
import me.simonxz.core.enchantments.PickaxeUpdater;
import me.simonxz.core.utils.Utils;

import me.simonxz.playermanager.users.User;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
public class EnchantUpgradeMenu implements Listener {

    private static Main plugin = Main.getPlugin(Main.class);
    EAPI api = new EAPI();
    EnchantPrice cost = new EnchantPrice();
    PickaxeUpdater update = new PickaxeUpdater();

    public void enchanter(Player p, String enchantment) {

        Inventory inv = Bukkit.createInventory(null, 27, "Enchanter " + enchantment);
        new BukkitRunnable() {
            @Override
            public void run() {

                if (!p.getOpenInventory().getTitle().equals("Enchanter " + enchantment)) {
                    cancel();
                }

                ItemStack item = p.getInventory().getItemInMainHand();
                Enchants enchant = plugin.getEnchantManager().getEnchant(enchantment);
                int currentLevel = api.getNBT(item, enchant.getEnchant());

                for(int i = 0; i < 27; ++i)
                    inv.setItem(i,Utils.makeItem(Material.BLACK_STAINED_GLASS_PANE, Utils.color("&r")));

                inv.setItem(10, item);
                if(currentLevel < enchant.getMax()) {
                    inv.setItem(12, Utils.makeItem(Material.LIGHT_GRAY_DYE, Utils.color("&a+1 "+enchant.getEnchant().replace("_", " ")),
                            Utils.color(" "),
                            Utils.color("&7Cost &6" + Utils.format(cost.getPrice(item, enchant.getEnchant(), 1)) + " Token(s)"),
                            Utils.color("&eLevel &f"+ api.getNBT(item, enchant.getEnchant()) +"&8/&7" + Utils.format(enchant.getMax())),
                            Utils.color(" "),
                            Utils.color("&aClick-here to purchase.")));
                    inv.setItem(13, Utils.makeItem(Material.LIGHT_GRAY_DYE, Utils.color("&a+10 "+enchant.getEnchant().replace("_", " ")),
                            Utils.color(" "),
                            Utils.color("&7Cost &6" + Utils.format(cost.getPrice(item, enchant.getEnchant(), 10)) + " Token(s)"),
                            Utils.color("&eLevel &f"+ api.getNBT(item, enchant.getEnchant()) +"&8/&7" + Utils.format(enchant.getMax())),
                            Utils.color(" "),
                            Utils.color("&aClick-here to purchase.")));
                    inv.setItem(14, Utils.makeItem(Material.LIGHT_GRAY_DYE, Utils.color("&a+50 "+enchant.getEnchant().replace("_", " ")),
                            Utils.color(" "),
                            Utils.color("&7Cost &6" + Utils.format(cost.getPrice(item, enchant.getEnchant(), 50)) + " Token(s)"),
                            Utils.color("&eLevel &f"+ api.getNBT(item, enchant.getEnchant()) +"&8/&7" + Utils.format(enchant.getMax())),
                            Utils.color(" "),
                            Utils.color("&aClick-here to purchase.")));
                    inv.setItem(15, Utils.makeItem(Material.LIGHT_GRAY_DYE, Utils.color("&a+100 "+enchant.getEnchant().replace("_", " ")),
                            Utils.color(" "),
                            Utils.color("&7Cost &6" + Utils.format(cost.getPrice(item, enchant.getEnchant(), 100)) + " Token(s)"),
                            Utils.color("&eLevel &f"+ api.getNBT(item, enchant.getEnchant()) +"&8/&7" + Utils.format(enchant.getMax())),
                            Utils.color(" "),
                            Utils.color("&aClick-here to purchase.")));
                    inv.setItem(16, Utils.makeItem(Material.LIGHT_GRAY_DYE, Utils.color("&a+1,000 "+enchant.getEnchant().replace("_", " ")),
                            Utils.color(" "),
                            Utils.color("&7Cost &6" + Utils.format(cost.getPrice(item, enchant.getEnchant(), 1000)) + " Token(s)"),
                            Utils.color("&eLevel &f"+ api.getNBT(item, enchant.getEnchant()) +"&8/&7" + Utils.format(enchant.getMax())),
                            Utils.color(" "),
                            Utils.color("&aClick-here to purchase.")));
                }
                else {
                    inv.setItem(12, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, Utils.color("&c&l NICE ONE! "),
                            Utils.color("&c Maximum "+enchantment+" level acquired! ")));
                    inv.setItem(13, Utils.makeItem(Material.RED_STAINED_GLASS, Utils.color("&c&l NICE ONE! "),
                            Utils.color("&c Maximum "+enchantment+" level acquired! ")));
                    inv.setItem(14, Utils.makeItem(Material.RED_WOOL, Utils.color("&c&l NICE ONE! "),
                            Utils.color("&c Maximum "+enchantment+" level acquired! ")));
                    inv.setItem(15, Utils.makeItem(Material.RED_STAINED_GLASS, Utils.color("&c&l NICE ONE! "),
                            Utils.color("&c Maximum "+enchantment+" level acquired! ")));
                    inv.setItem(16, Utils.makeItem(Material.RED_STAINED_GLASS_PANE, Utils.color("&c&l NICE ONE! "),
                            Utils.color("&c Maximum "+enchantment+" level acquired! ")));
                }
            }
        }.runTaskTimer(plugin, 0, 10L);
        p.openInventory(inv);
        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 2, 2);

    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if(e.getView().getTitle().contains("Enchanter ")) {

            e.setCancelled(true);

            final ItemStack clickedItem = e.getCurrentItem();

            if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

            Player p = (Player) e.getWhoClicked();
            String[] title = ChatColor.stripColor(e.getView().getTitle()).split(" ");
            String enchant = title[1];
            String[] clickedName = ChatColor.stripColor(clickedItem.getItemMeta().getDisplayName()).split(" ");
            int amount = Utils.convertInteger(clickedName[0].replace("+", "").replace(",", ""));
            ItemStack item = p.getInventory().getItemInMainHand();

            if(plugin.getEnchantManager().getEnchant(enchant)!=null&&amount>0) {

                if(api.getNBT(item, enchant)+amount<=plugin.getEnchantManager().getEnchant(enchant).getMax()) {

                    User user = plugin.getManager().getPlayerManager().getUser(p.getUniqueId());
                    long price = cost.getPrice(item, enchant, amount);
                    if(user.getTokens()>=price) {
                        user.removeTokens(price);
                        api.setNBT(item, enchant, api.getNBT(item, enchant)+amount);
                        update.updatePickaxe(item);
                        p.sendMessage(Utils.color("&aYou purchased &f" + Utils.format(amount) + "&b " + enchant.replace("_", " ") + "&a Levels for &6"
                                + Utils.format(price) + " Token(s)")	);
                        p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 3, 3);
                    }
                    else {
                        p.sendMessage(Utils.color("&7You need &6" + Utils.format(price) + " Token(s)&7 to purchase &f"
                                + Utils.format(amount) + "&b " + enchant.replace("_", " ") + "&7 Levels"));
                    }

                }
                else {
                    p.sendMessage(Utils.color("&cYou cannot exceed enchantment level "
                            + Utils.format(plugin.getEnchantManager().getEnchant(enchant).getMax()) + " for " + enchant.replace("_", " ")));
                }

            }
            return;
        }
    }
}
