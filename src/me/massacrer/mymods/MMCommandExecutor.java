package me.massacrer.mymods;

import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

class MMCommandExecutor implements CommandExecutor {
	private MassacrersMod plugin = null;
	
	MMCommandExecutor(MassacrersMod plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command,
			String label, String[] args) {
		if (!(sender instanceof Player)) {
			dealWithConsole(sender);
			return true;
		}
		
		Player player = (Player) sender;
		
		if (!(plugin.isAllowed(player))) {
			player.sendMessage(ChatColor.RED + "");
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
						player.sendMessage(ChatColor.RED
								+ "Invalid player name or syntax. Syntax is <command> hat <player> <block>");
					}
					return true;
				}
				plugin.fitCustomHat(player, args[1]);
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
					snipe = true;
					activePlayer = player;
				}
				plugin.redstoneSnipe(activePlayer, snipe);
				return true;
			}
			if (args[0].equalsIgnoreCase("t")) {
				int radius = 6;
				if (args.length > 1) {
					try {
						radius = Integer.parseInt(args[1]);
					} catch (NumberFormatException e) {
						player.sendMessage(ChatColor.RED
								+ "Invalid radius specified, using default (6)");
					}
				}
				plugin.tntNeutralise(player, radius);
				return true;
			}
			if (args[0].equalsIgnoreCase("mode")) {
				if (args.length > 0) {
					if (args[1].equalsIgnoreCase("s")) {
						player.setGameMode(GameMode.SURVIVAL);
						return true;
					}
					if (args[1].equalsIgnoreCase("c")) {
						player.setGameMode(GameMode.CREATIVE);
						return true;
					}
					for (Player p : plugin.getServer().getOnlinePlayers()) {
						if (p.getName().equalsIgnoreCase(args[1])) {
							if (args.length > 1) {
								if (args[2].equalsIgnoreCase("s")) {
									p.setGameMode(GameMode.SURVIVAL);
								}
								if (args[2].equalsIgnoreCase("c")) {
									p.setGameMode(GameMode.CREATIVE);
								}
							}
							player.sendMessage(ChatColor.DARK_AQUA
									+ "Current game mode for " + p.getName()
									+ ": " + gameModeString(p));
							
							return true;
						}
					}
					
				} else {
					player.sendMessage(ChatColor.DARK_AQUA
							+ "Current game mode: " + gameModeString(player));
				}
			}
		} else {
			player.sendMessage(ChatColor.DARK_AQUA + "MPack is active");
		}
		
		return false;
	}
	
	private void dealWithConsole(CommandSender sender) {

		// TODO: deal with console
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
