package me.massacrer.mymods;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
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
					sender.sendMessage(ChatColor.RED + "Invalid command usage, please specify a player");
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
						sender.sendMessage(ChatColor.RED + "Invalid command usage, please specify a player");
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
								+ "Invalid radius specified, using default (6)");
					}
				}
				plugin.tntNeutralise((Player) sender, radius);
				return true;
			}
			if (args[0].equalsIgnoreCase("mode")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("s") && player) {
						((Player) sender).setGameMode(GameMode.SURVIVAL);
						return true;
					}
					if (args[1].equalsIgnoreCase("c") && player) {
						((Player) sender).setGameMode(GameMode.CREATIVE);
						return true;
					}
					for (Player p : plugin.getServer().getOnlinePlayers()) {
						if (p.getName().equalsIgnoreCase(args[1])) {
							if (args.length > 2) {
								if (args[2].equalsIgnoreCase("s")) {
									p.setGameMode(GameMode.SURVIVAL);
								}
								if (args[2].equalsIgnoreCase("c")) {
									p.setGameMode(GameMode.CREATIVE);
								}
							}
							sender.sendMessage(ChatColor.DARK_AQUA
									+ "Current game mode for " + p.getName()
									+ ": " + gameModeString(p));
							
							return true;
						}
					}
				} else if (player) {
					sender.sendMessage(ChatColor.DARK_AQUA
							+ "Current game mode: "
							+ gameModeString((Player) sender));
				}
			}
			if (args[0].equalsIgnoreCase("stealth")) {
				if (args.length > 1) {
					if (args[1].equalsIgnoreCase("on")) {
						plugin.stealthServer(true);
						sender.sendMessage("Server now hidden");
						return true;
					}
					if (args[1].equalsIgnoreCase("off")) {
						plugin.stealthServer(false);
						sender.sendMessage("Server now visible");
						return true;
					}
				}
				sender.sendMessage(ChatColor.DARK_AQUA + "Server is currently "
						+ (plugin.serverStealthed ? "hidden" : "visible"));
				return true;
			}
		} else {
			sender.sendMessage(ChatColor.DARK_AQUA + "MPack is active");
			return true;
		}
		
		return false;
	}
	
	private static String gameModeString(Player player) {
		switch (player.getGameMode()) {
			case SURVIVAL:
				return "survival";
			case CREATIVE:
				return "creative";
			default:
				return "";
		}
	}
}
