package me.mdspace.credits.shop;

import me.mdspace.credits.CreditManager;
import me.mdspace.credits.CreditSQL;
import me.mdspace.credits.Credits;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ShopListener implements Listener {

	CreditSQL sql = Credits.getSQL();

	@EventHandler
	public void onPlayerShop(InventoryClickEvent event) {
		if (!event.getInventory().getName().contains("Shop!")) return;
		event.setCancelled(true);
		if (event.getCurrentItem() == null) {
			event.setCancelled(true);
			return;
		}
		try {
			Player player = (Player) event.getWhoClicked();
			int total = CreditManager.getCredits(player);
			int cost = Integer.parseInt(event.getCurrentItem().getItemMeta().getLore().get(0).replaceFirst(ChatColor.GOLD + "Credits : ", ""));
			if (event.getCurrentItem().getItemMeta().getLore().get(1).contains("donator only")) {
				if (!player.hasPermission("creditshop.donoracsess")) {
					player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.DARK_RED + "This item is donator only please do /donate");
					return;
				}
			}
			if (total < cost) {
				player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Not Enough credits to buy this item!");
				return;
			}
			if (player.getInventory().firstEmpty() == -1) {
				player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.RED + "Inventory full! you must have a free slot!");
				return;
			}
			CreditManager.removeCredits(player, cost);
			player.getInventory().addItem(Shop.get().items.get(event.getCurrentItem().getItemMeta().getDisplayName()));
			player.sendMessage(Credits.getPlugin().getPrefix() + ChatColor.GREEN + "Item added to your inventory!");
			event.setCancelled(true);
			return;
		} catch (Exception e) {
			event.setCancelled(true);
		}
	}

}
