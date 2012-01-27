package me.massacrer.mymods;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;

class MMListener implements Listener {
	MassacrersMod plugin = null;
	
	MMListener(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onServerListPing(ServerListPingEvent e) {
		plugin.writePingEventToLog(e.getAddress());
		if (plugin.serverStealthed) {
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerLogin(PlayerLoginEvent e) {
		if(plugin.serverLocked) {
			if (!e.getPlayer().getName().equalsIgnoreCase("Massacrer")){
				e.disallow(Result.KICK_FULL, "This server is currently not accepting join requests");
			}
		}
	}
}