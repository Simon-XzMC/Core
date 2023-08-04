package me.simonxz.core.pickaxes;

import me.simonxz.playermanager.PlayerManager;
import me.simonxz.playermanager.users.User;
import me.simonxz.playermanager.users.UserManager;
import org.bukkit.entity.Player;


public class Backpacks {

        UserManager userManager = PlayerManager.getInstance().getPlayerManager();


        // Configuration stuff for backpacks
        private long maxCapacity = 1000000;
        private long capacityCost = 100000;
        private long capacityPerUpgrade = 1000;


        public void UpgradeBackpack(Player p) {
            User user = this.userManager.getUser(p.getUniqueId());

            if(user.getBackpackCapacity() >= maxCapacity) {
                p.sendMessage("You have the maximum allowed capacity.");
                return;
            }

            if(user.getTokens() < capacityCost) {
                p.sendMessage("Not enough tokens to upgrade backpack.");
                return;
            }

            user.addBackpackCapacity(capacityPerUpgrade);
            user.removeTokens(capacityCost);
            p.sendMessage("You upgraded your max capacity for " + capacityCost + " tokens!");

        }

        public void SellBackpack(Player p) {
            User user = this.userManager.getUser(p.getUniqueId());

            if(user.getBackpackContent() <= 0) {
                p.sendMessage("You have no items in your backpack to sell.");
                return;
            }

            double value = user.getBackpackValue();
            long contents = user.getBackpackContent();

            user.setBackpackValue(0);
            user.setBackpackContent(0);

            p.sendMessage("You sold " + contents + " for $" + value);
            // add here your economy stuff to give the money

        }
}
