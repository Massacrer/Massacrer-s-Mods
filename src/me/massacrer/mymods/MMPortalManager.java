package me.massacrer.mymods;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class MMPortalManager {
	private MassacrersMod plugin = null;
	
	MMPortalManager(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
}

class MMPortal {
	Player[] canSee = null;
	Location origin = null;
	
	MMPortal(Location loc, Player[] canSee) {
		this.canSee = canSee;
		this.origin = loc;
	}
}