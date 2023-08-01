package me.simonxz.core.commands;

import me.simonxz.core.utils.CreatePickaxe;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PickaxeCommand implements CommandExecutor {
    CreatePickaxe pick = new CreatePickaxe();

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("pick")) {
            if (sender instanceof Player) {
                Player p = (Player)sender;
                this.pick.defaultPickaxe(p);
                return true;
            }
            return true;
        }
        return false;
    }
}
