package me.massacrer.mymods;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

class MMCommandExecutor implements CommandExecutor {
	private MassacrersMod plugin = null;
	
	MMCommandExecutor(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		boolean player = sender instanceof Player;
		if (sender instanceof ConsoleCommandSender) {
			player = false;
		}
		
		if (!(plugin.isAllowed(sender))) {
			sender.sendMessage(ChatColor.RED + "Not allowed");
			return true;
		}
		
		if (args.length > 0) {
			if (args[0].equalsIgnoreCase("hat")
					|| args[0].equalsIgnoreCase("a")) {
				if (args.length > 3) {
					Player p = plugin.getServer().getPlayer(args[1]);
					if (p != null) {
						plugin.fitCustomHat(p, args[1]);
					} else {
						sender.sendMessage(ChatColor.RED
								+ "Invalid player name or syntax. Syntax is <command> hat <player> <block>");
					}
					return true;
				}
				if (!player) {
					sender.sendMessage(ChatColor.RED
							+ "Invalid command usage, please specify a player");
					return true;
				}
				plugin.fitCustomHat((Player) sender, args[1]);
				return true;
			}
			if (args[0].equalsIgnoreCase("r")) {
				Boolean snipe;
				Player activePlayer;
				
				if (args.length > 1) {
					snipe = false;
					activePlayer = plugin.getServer().getPlayer(args[1]);
					if (activePlayer == null) {
						sender.sendMessage(ChatColor.RED + "Player not found");
						return true;
					}
				} else {
					if (!player) {
						sender.sendMessage(ChatColor.RED
								+ "Invalid command usage, please specify a player");
					}
					snipe = true;
					activePlayer = (Player) sender;
				}
				plugin.redstoneSnipe(activePlayer, snipe);
				return true;
			}
			if (args[0].equalsIgnoreCase("t") && player) {
				int radius = 8;
				if (args.length > 1) {
					try {
						radius = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						sender.sendMessage(ChatColor.RED
								+ "Invalid radius specified, using default (8)");
					}
				}
				plugin.tntNeutralise((Player) sender, radius);
				return true;
			}
			if (args[0].equalsIgnoreCase("stealth")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("on")) {
						plugin.stealthServer(true);
					}
					if (args[1].equalsIgnoreCase("off")) {
						plugin.stealthServer(false);
					}
				}
				sender.sendMessage(ChatColor.DARK_AQUA + "Server is currently "
						+ (plugin.serverStealthed ? "hidden" : "visible"));
				return true;
			}
			if (args[0].equalsIgnoreCase("lock")) {
				plugin.lockServer(true);
				sender.sendMessage(ChatColor.DARK_AQUA + "Server is now locked");
				return true;
			}
			if (args[0].equalsIgnoreCase("unlock")) {
				plugin.lockServer(false);
				sender.sendMessage(ChatColor.DARK_AQUA
						+ "Server is now unlocked");
				return true;
			}
			if (args[0].equalsIgnoreCase("fly") && player) {
				boolean canFly = true;
				if (args.length > 1 && args[1].equalsIgnoreCase("0")) {
					canFly = false;
				}
				((Player) sender).setAllowFlight(canFly);
			}
		} else {
			sender.sendMessage(ChatColor.DARK_AQUA + "MPack is active");
			return true;
		}
		
		return false;
	}
}
