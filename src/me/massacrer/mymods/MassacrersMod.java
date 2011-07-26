package me.massacrer.mymods;

import java.util.logging.Logger;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class MassacrersMod extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	MMCommandExecutor commandExecutor = new MMCommandExecutor(this);
	
	public void onDisable() {
		log.info("Massacrer's Mod disabled");
	}
	
	public void onEnable() {
		log.info("Massacrer's Mod enabled");
	}
	
	void fitCustomArmour(Player player, String[] args) {
		// TODO: fit custom armour
	}
	
	boolean isAllowed(Player player) {
		if (player.hasPermission("massacrer.armour"))
			return true;
		if (player.isOp())
			return true;
		return false;
	}
}
