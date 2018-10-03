package me.skizzy.playerinfo;

import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class core extends JavaPlugin implements Listener {
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable() {
		this.log.info("[PlayerInfo] PlayerInfo has been enabled.");
		this.log.info("[PlayerInfo] Detected config version: " + getConfig().get("config-version"));
		getConfig().options().copyDefaults(true);
		getConfig().addDefault("config-version", Integer.valueOf(1));
		saveConfig();
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(this, this);
	}

	public void onDisable() {
		this.log.info("[PlayerInfo] PlayerInfo has been disabled.");
		saveConfig();
	}

	@SuppressWarnings("deprecation")
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (cmd.getName().equalsIgnoreCase("pinfo")) {
			Player player = (Player) sender;
			if (player.hasPermission("playerinfo.use")) {
				if (args.length == 0 || args.length >= 2) {
					sender.sendMessage("");
					sender.sendMessage(" §b§lPlayer Information");
					sender.sendMessage(ChatColor.GOLD + " Usage: " + ChatColor.GRAY + "/pinfo <player>");
					sender.sendMessage("");
					return true;
				}
				Player target = Bukkit.getServer().getPlayer(args[0]);
				if (target == null) {
					sender.sendMessage("");
					sender.sendMessage(" §b§lPlayer Information");
					sender.sendMessage(" §7Cannot find player " + args[0] + ".");
					sender.sendMessage("");
					return true;
				}
				sender.sendMessage("");
				sender.sendMessage(" §b§lPlayer Info for: §7" + target.getName());
				sender.sendMessage(ChatColor.GOLD + " IP Address: §7" + target.getAddress().getAddress());
				sender.sendMessage(ChatColor.GOLD + " Is OP: §7" + target.isOp());
				sender.sendMessage(ChatColor.GOLD + " Health: §7" + target.getHealth() + "/" + target.getMaxHealth());
				// sender.sendMessage(ChatColor.GOLD + " Location: " +
				// player.getWorld().getName() + ", "
				// + player.getLocation().getX() + ", " +
				// player.getLocation().getY() + ", "
				// + player.getLocation().getZ());
				sender.sendMessage(ChatColor.GOLD + " Gamemode: §7" + target.getGameMode().name().toUpperCase());
				sender.sendMessage(ChatColor.GOLD + " Food Level: §7" + target.getFoodLevel() + "/20");
				sender.sendMessage(ChatColor.GOLD + " Item In Hand: §7" + target.getItemInHand().getType() + ", "
						+ target.getItemInHand().getTypeId());
				sender.sendMessage(ChatColor.GOLD + " EXP Level: §7" + target.getLevel());
				sender.sendMessage("");
			} else if (!player.hasPermission(getConfig().getString("permission-node"))) {
				player.sendMessage(
						getConfig().getString("no-perm").replaceAll("&", "§").replaceAll("%player%", player.getName()));
			}
			return true;
		}
		return false;
	}
}