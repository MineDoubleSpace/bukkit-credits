package me.mdspace.credits.shop;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;

import me.mdspace.credits.Credits;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;

public class Shop {
	private static Shop instace;
	private Inventory inv;
	public HashMap<String, ItemStack> items = new HashMap<>();

	@SuppressWarnings("deprecation")
	public void setup() {
		inv = Bukkit.createInventory(null, 54, ChatColor.BLACK + "Shop!");
		ResultSet set = Credits.getSQL().executeQuery("SELECT * FROM  shop LIMIT 0 , 54");
		// id, name, enchaments, cost, byte
		try {
			while (set.next()) {
				int ID = set.getInt("item");
				String Name = set.getString("name");
				String enchantments = set.getString("enchantments");
				int cost = set.getInt("cost");
				byte bit = (byte) set.getInt("byte");
				String donator = set.getString("donator");
				ItemStack stack = new ItemStack(ID);
				if (bit != '0') {
					stack = new ItemStack(ID, 0, bit);
					MaterialData data = new MaterialData(ID, bit);
					stack.setData(data);
				}
				ItemMeta meta = stack.getItemMeta();
				if (enchantments.contains(",")) {
					String[] echantment = enchantments.split(",");
					for (String s : echantment) {
						String[] args = s.split(":");
						meta.addEnchant(Enchantment.getById(Integer.parseInt(args[0])), Integer.parseInt(args[1]), true);
					}
				} else {
					String[] args = enchantments.split(":");
					meta.addEnchant(Enchantment.getById(Integer.parseInt(args[0])), Integer.parseInt(args[1]), true);
				}
				meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', Name));
				ItemMeta display = meta;
				ItemStack displayItem = stack;
				String dmsg = " ";
				if (donator.equalsIgnoreCase("true")) {					
					dmsg = ChatColor.RED + "This item is donator only!";
				}
				display.setLore(Arrays.asList(ChatColor.GOLD + "Credits : " + cost, dmsg));
				stack.setItemMeta(meta);
				displayItem.setItemMeta(display);
				inv.addItem(displayItem);
				items.put(Name, stack);
			}
		} catch (SQLException e) {
			System.out.println("Failed to get shop items from DB");
		}
	}

	public static Shop get() {
		if (instace == null) {
			instace = new Shop();
			instace.setup();
		}
		return instace;
	}

	public void openShop(Player player) {
		player.openInventory(inv);
	}
}
