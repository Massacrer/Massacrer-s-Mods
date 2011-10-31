package me.massacrer.mymods;

import org.bukkit.event.server.ServerListPingEvent;
import org.bukkit.event.server.ServerListener;

class MMServerListener extends ServerListener {
	MassacrersMod plugin = null;
	
	MMServerListener(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	public void onServerListPing(ServerListPingEvent e) {
		MassacrersMod.log.info("Ping recieved from address " + e.toString());
	}
}