package me.massacrer.mymods;

import java.util.HashMap;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class MassacrersMod extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	MMCommandExecutor commandExecutor = new MMCommandExecutor(this);
	PluginManager pm = getServer().getPluginManager();
	
	public void onDisable() {
		log.info("Massacrer's Mod disabled");
	}
	
	public void onEnable() {
		getCommand("m").setExecutor(commandExecutor);
		getCommand("mm").setExecutor(commandExecutor);
		getCommand("mmod").setExecutor(commandExecutor);
		log.info("Massacrer's Mod enabled");
	}
	
	void fitCustomArmour(Player player, String[] args) {
		// args[0] is the armour command string
		if (args.length < 3) {
			reportInvalidInput(player);
			return;
		} else {
			int armourSlot = getPlayerSlot(args[1]);
			int materialId = -1;
			
			if (armourSlot == 0) {
				reportInvalidInput(player);
				return;
			}
			
			try {
				materialId = Integer.parseInt(args[2]);
			} catch (NumberFormatException ex) {}
			if (materialId == -1) {
				materialId = Material.matchMaterial(args[2]).getId();
			}
			if (materialId < 1) {
				player.sendMessage(ChatColor.RED
						+ "Material must be valid in either string or integer form");
				return;
			}
			if (materialId >= 256) {
				player.sendMessage(ChatColor.RED
						+ "Material value is invalid; specify one between 0 and 256 for valid results");
				return;
			}
			
			ItemStack is = new ItemStack(materialId, 1);
			PlayerInventory inv = player.getInventory();
			
			if (armourSlot == 1) {
				if (inv.getHelmet().getTypeId() >= 256) {
					HashMap<Integer, ItemStack> items = inv.addItem(inv
							.getHelmet());
					for (int i = 0; i < items.size(); i++) {
						player.getWorld().dropItemNaturally(
								player.getEyeLocation(), items.get(i));
					}
				}
				inv.setHelmet(is);
			}
			if (armourSlot == 2) {
				if (inv.getChestplate().getTypeId() >= 256) {
					HashMap<Integer, ItemStack> items = inv.addItem(inv
							.getChestplate());
					for (int i = 0; i < items.size(); i++) {
						player.getWorld().dropItemNaturally(
								player.getEyeLocation(), items.get(i));
					}
				}
				inv.setChestplate(is);
			}
			if (armourSlot == 3) {
				if (inv.getLeggings().getTypeId() >= 256) {
					HashMap<Integer, ItemStack> items = inv.addItem(inv
							.getLeggings());
					for (int i = 0; i < items.size(); i++) {
						player.getWorld().dropItemNaturally(
								player.getEyeLocation(), items.get(i));
					}
				}
				inv.setLeggings(is);
			}
			if (armourSlot == 4) {
				if (inv.getBoots().getTypeId() >= 256) {
					HashMap<Integer, ItemStack> items = inv.addItem(inv
							.getBoots());
					for (int i = 0; i < items.size(); i++) {
						player.getWorld().dropItemNaturally(
								player.getEyeLocation(), items.get(i));
					}
				}
				inv.setBoots(is);
			}
		}
	}
	
	boolean isAllowed(Player player) {
		if (player.hasPermission("massacrer.armour"))
			return true;
		if (player.isOp())
			return true;
		return false;
	}
	
	int getPlayerSlot(String str) {
		if (str.equalsIgnoreCase("head"))
			return 1;
		if (str.equalsIgnoreCase("body") || str.equalsIgnoreCase("chest"))
			return 2;
		if (str.equalsIgnoreCase("legs"))
			return 3;
		if (str.equalsIgnoreCase("feet") || str.equalsIgnoreCase("boots"))
			return 4;
		return 0;
	}
	
	void reportInvalidInput(Player p) {
		p.sendMessage(ChatColor.DARK_AQUA
				+ "Specify correct arguments for this function");
	}
}
