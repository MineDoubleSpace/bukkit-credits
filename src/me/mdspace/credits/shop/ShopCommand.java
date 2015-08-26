package me.mdspace.credits.shop;

import me.mdspace.credits.Credits;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}
		if (Credits.getPlugin().isPermissions()) {
			if (!sender.hasPermission("credits.shop")) {
				sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.DARK_RED + "No permissions!");
				return true;
			}
		}
		Shop.get().openShop((Player) sender);
		return true;
	}

}
