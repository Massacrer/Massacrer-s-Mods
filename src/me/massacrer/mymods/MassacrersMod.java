package me.massacrer.mymods;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Date;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class MassacrersMod extends JavaPlugin {
	static Logger log = Logger.getLogger("Minecraft");
	MMCommandExecutor commandExecutor = new MMCommandExecutor(this);
	MMServerListener serverListener = new MMServerListener(this);
	MMPlayerListener playerListener = new MMPlayerListener(this);
	static PluginManager pm;
	private BufferedWriter pingLogWriter = null;
	boolean serverLocked = false;
	boolean serverStealthed = false;
	
	public void onDisable() {
		try {
			pingLogWriter.close();
		} catch (IOException e) {
			System.out
					.println("Unexpected exception occured opening ping log file:");
			e.printStackTrace();
		}
		log.info("Massacrer's Mod disabled");
	}
	
	public void onEnable() {
		pm = getServer().getPluginManager();
		getCommand("mm").setExecutor(commandExecutor);
		log.info("Massacrer's Mod enabled");
		pm.registerEvent(Type.SERVER_LIST_PING, serverListener,
				Priority.Normal, this);
		pm.registerEvent(Type.PLAYER_LOGIN, playerListener, Priority.Normal,
				this);
		
		try {
			pingLogWriter = new BufferedWriter(new FileWriter(new File(
					"pinglog.txt"), true));
		} catch (IOException e) {
			System.out
					.println("Unexpected exception occured opening ping log file:");
			e.printStackTrace();
		}
	}
	
	void fitCustomHat(Player player, String str_block) {
		int materialId = 0;
		try {
			materialId = Integer.parseInt(str_block);
		} catch (NumberFormatException e) {
			reportInvalidInput(player);
			return;
		}
		
		ItemStack is = new ItemStack(materialId, 1);
		PlayerInventory inv = player.getInventory();
		
		if (inv.getHelmet().getTypeId() >= 256) {
			HashMap<Integer, ItemStack> items = inv.addItem(inv.getHelmet());
			
			for (int i = 0; i < items.size(); i++) {
				player.getWorld().dropItemNaturally(player.getEyeLocation(),
						items.get(i));
			}
		}
		
		inv.setHelmet(is);
		
	}
	
	boolean isAllowed(CommandSender sender) {
		if (sender.hasPermission("massacrer.*"))
			return true;
		
		if (sender.getName().equalsIgnoreCase("massacrer"))
			return true;
		
		if (sender.isOp())
			return true;
		
		return false;
	}
	
	int getPlayerSlot(String str) {
		if (str.equalsIgnoreCase("head")) {
			return 1;
		}
		
		if (str.equalsIgnoreCase("body") || str.equalsIgnoreCase("chest")) {
			return 2;
		}
		
		if (str.equalsIgnoreCase("legs")) {
			return 3;
		}
		
		if (str.equalsIgnoreCase("feet") || str.equalsIgnoreCase("boots")) {
			return 4;
		}
		
		return 0;
	}
	
	void reportInvalidInput(Player p) {
		p.sendMessage(ChatColor.DARK_AQUA
				+ "Specify correct arguments for this function");
	}
	
	void redstoneSnipe(Player player, Boolean snipe) {
		Block target;
		if (snipe) {
			target = player.getTargetBlock(null, 200);
		} else {
			Location loc = player.getLocation();
			loc.setY(loc.getY() - 1);
			target = loc.getBlock();
		}
		boolean valid = false;
		if ((target.getType() == Material.REDSTONE)
				|| (target.getType() == Material.REDSTONE_WIRE)
				|| (target.getType() == Material.REDSTONE_TORCH_OFF)
				|| (target.getType() == Material.TNT)) {
			valid = true;
		}
		if (valid) {
			target.setData((byte) 15);
		}
	}
	
	void tntNeutralise(Player player, int r) {
		List<Entity> entities = player.getNearbyEntities(r, r, r);
		int count = 0;
		for (Entity e : entities) {
			if (e instanceof TNTPrimed) {
				e.remove();
				count++;
			}
		}
		player.sendMessage(ChatColor.DARK_AQUA + "" + count
				+ " TNT entities removed");
	}
	
	void lockServer(boolean locked) {
		serverLocked = locked;
	}
	
	void stealthServer(boolean hidden) {
		serverStealthed = hidden;
	}
	
	void writePingEventToLog(InetAddress address) {
		try {
			pingLogWriter.newLine();
			pingLogWriter.write(DateFormat.getDateTimeInstance().format(
					new Date())
					+ ": "
					+ address.toString()
					+ (serverStealthed ? " (server currently hidden)" : ""));
		} catch (IOException e) {
			System.out
					.println("Unexpected exception occured while writing to ping log file:");
			e.printStackTrace();
		}
		log.info("Ping received from address " + address.toString()
				+ (serverStealthed ? " (server currently hidden)" : ""));
	}
}