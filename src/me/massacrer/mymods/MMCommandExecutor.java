package me.massacrer.mymods;

import org.bukkit.ChatColor;
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
		} else {
			Player player = (Player) sender;
			
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
							sender.sendMessage(ChatColor.RED
									+ "Player not found");
							return true;
						}
					} else {
						snipe = true;
						activePlayer = player;
					}
					plugin.redstoneSnipe(activePlayer, snipe);
					return true;
				}
			} else {
				player.sendMessage(ChatColor.DARK_AQUA + "MPack is active");
			}
		}
		
		return false;
	}
	
	private void dealWithConsole(CommandSender sender) {

		// TODO: deal with console
	}
}
