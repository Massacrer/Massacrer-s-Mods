package me.massacrer.mymods;

import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServerListener;

class MMServerListener extends ServerListener {
	MassacrersMod plugin = null;
	
	MMServerListener(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	public void onServerListPing(ServerListPingEvent e) {
		plugin.writePingEventToLog(e.getAddress());
		if (plugin.serverStealthed) {
			e.setCancelled(true);
		}
	}
}

class MMPlayerListener extends PlayerListener {
	MassacrersMod plugin = null;
	MMPlayerListener (MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	public void onPlayerLogin(PlayerLoginEvent e) {
		if(plugin.serverLocked) {
			if (!e.getPlayer().getName().equalsIgnoreCase("Massacrer")){
				e.disallow(Result.KICK_FULL, "This server is currently not accepting join requests");
			}
		}
	}
}