package me.simonxz.core.enchantments;

import me.simonxz.core.Main;
import me.simonxz.core.api.Enchants;
import me.simonxz.core.utils.Utils;
import me.simonxz.playermanager.users.User;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
public class EnchantMax {
    private static Main plugin = (Main)Main.getPlugin(Main.class);

    EAPI api = new EAPI();

    EnchantPrice p = new EnchantPrice();

    PickaxeUpdater update = new PickaxeUpdater();

    public void enchantMax(Player player, String enchantment) {
        User user = plugin.getManager().getPlayerManager().getUser(player.getUniqueId());
        Enchants enchant = plugin.getEnchantManager().getEnchant(enchantment);
        if (enchant == null)
            return;
        ItemStack item = player.getInventory().getItemInMainHand();
        int maxLevel = enchant.getMax();
        int currentLevel = this.api.getNBT(item, enchant.getEnchant());
        int untilMax = maxLevel - currentLevel;
        int purchasedLevel = 0;
        long cost = 0L;
        long tokens = user.getTokens();
        for (int i = 1; i <= untilMax; i++) {
            long extra = (long)(enchant.getPrice() + currentLevel * enchant.getPrice() * enchant.getExponent());
            if (cost + extra > tokens)
                break;
            purchasedLevel++;
            currentLevel++;
            cost += extra;
        }
        if (purchasedLevel > 0) {
            user.removeTokens(cost);
            this.api.setNBT(item, enchant.getEnchant(), this.api.getNBT(item, enchant.getEnchant()) + purchasedLevel);
            this.update.updatePickaxe(item);
            player.sendMessage(Utils.color("&aYou purchased &f" + Utils.format(purchasedLevel) + "&b " +
                    enchant.getEnchant().replace("_", " ") + "&a Levels for &6" + Utils.format(cost) + " Token(s)"));
        }
    }
}
