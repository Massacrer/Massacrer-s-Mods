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
		if(!(sender instanceof Player)) {
			dealWithConsole(sender);
		} else {
			Player player = (Player) sender;
			if (args.length > 0) {
				if (args[0].equalsIgnoreCase("skin") || args[0].equalsIgnoreCase("a")) {
					plugin.fitCustomArmour(player, args);
				}
			} else {
				player.sendMessage(ChatColor.DARK_AQUA + "MPack is active");
			}
		}
		return false;
	}
	private void dealWithConsole(CommandSender sender) {
		//TODO: deal with console
	}
}
