package me.mdspace.credits;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.entity.Player;

public class CreditManager {

	private static CreditSQL csql = Credits.getSQL();

	public static int getCredits(Player name) {
		if (csql.containsPlayer(name)) {
			ResultSet set = csql.getRowByColumn("Credits", "Username", name.getName());
			try {
				if (!set.next()) {
					System.out.println("Error!! Something went wrong");
				}
				return set.getInt("Credits");
			} catch (SQLException e) {
				return 0;
			}
		}
		return 0;
	}
	
	public static double getMulti(Player player) {
		if (player.hasPermission("credits.multiplier.2")) return 2.0;
		if (player.hasPermission("credits.multiplier.1.75")) return 1.75;
		if (player.hasPermission("credits.multiplier.1.5")) return 1.5;
		if (player.hasPermission("credits.multiplier.1.25")) return 1.25;
		return 1;
	}

	public static void addPlayer(Player player) {
		String[] col = new String[] { "Username", "Credits" };
		String[] val = new String[] { "'" + player.getName() + "'", "'" + Credits.getPlugin().getStartAmount() + "'" };
		csql.insertInto("Credits", col, val);
	}

	public static void setCredits(Player player, int amount) {
		if (csql.containsPlayer(player)) {
			csql.executeUpdate("UPDATE Credits SET Credits='" + amount + "' WHERE Username='" + player.getName() + "'");
		} else {
			addPlayer(player);
		}
	}

	public static void removeCredits(Player player, int amount) {
		int total = getCredits(player);
		int set = total - amount;
		setCredits(player, set);
	}

	public static void addCredits(Player player, int amount) {
		setCredits(player, getCredits(player) + amount);
	}

}
