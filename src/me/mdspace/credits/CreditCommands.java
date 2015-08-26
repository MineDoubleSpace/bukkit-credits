package me.mdspace.credits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreditCommands implements CommandExecutor {


	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GOLD + CreditManager.getCredits((Player) sender) + " Credits");
				return true;
			} else {
				sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Must be a player!");
				return true;
			}
		}
		if (!sender.hasPermission("credits.admin")) {
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "No permissions!");
			return true;
		}
		if (args.length < 3) {
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Wrong format! Please use \"/credits <command> <player> <amount>\"");
			return true;
		}
		Player player = Bukkit.getPlayer(args[1]);
		if (player == null) {
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Player " + args[1] + "not found!");
			return true;
		}
		int amount;
		try {
			amount = Integer.parseInt(args[2]);
		} catch (NumberFormatException e) {
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Amount must be a number!");
			return true;
		}
		if (amount < 1) {
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Amount must be a positive number!");
			return true;
		}
		if (args[0].equalsIgnoreCase("multiplier")){
			int total = (int) (amount * CreditManager.getMulti(player));
			CreditManager.addCredits(player, total);
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "" + total + " Credits Added to " + player.getName() + " with multiplier of " + CreditManager.getMulti(player));
			player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "" + total + " Credits added!");
			return true;
		}
		if (args[0].equalsIgnoreCase("set")) {
			CreditManager.setCredits(player, amount);
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "Credits set " + amount + " for " + player.getName());
			return true;
		}
		if (args[0].equalsIgnoreCase("add")) {
			CreditManager.addCredits(player, amount);
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "" + amount + " Credits Added to " + player.getName());
			player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "" + amount + " Credits added!");
			return true;
		}
		if (args[0].equalsIgnoreCase("remove")) {
			CreditManager.removeCredits(player, amount);
			sender.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "" + amount + " Credits removed from " + player.getName());
			return true;
		}
		return true;
	}

}
