package me.mdspace.credits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class CreditListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (Credits.getPlugin().getStartAmount() == 0) {
			return;
		}
		if (!Credits.getSQL().containsPlayer(player)) {
			CreditManager.setCredits(player, Credits.getPlugin().getStartAmount());
		}
	}

}
