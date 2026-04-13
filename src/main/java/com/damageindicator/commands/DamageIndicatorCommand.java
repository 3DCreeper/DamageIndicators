package com.damageindicator.commands;

import com.damageindicator.DamageIndicatorPlugin;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamageIndicatorCommand implements CommandExecutor {

    private final DamageIndicatorPlugin plugin;

    public DamageIndicatorCommand(DamageIndicatorPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("damageindicator.toggle")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command!");
            return true;
        }

        if (args.length == 0) {
            plugin.getPreferenceManager().toggle(player);
            boolean enabled = plugin.getPreferenceManager().isEnabled(player);
            String status = enabled ? ChatColor.GREEN + "enabled" : ChatColor.RED + "disabled";
            player.sendMessage(ChatColor.YELLOW + "Damage indicator " + status + ChatColor.YELLOW + "!");
            return true;
        }

        String arg = args[0].toLowerCase();
        switch (arg) {
            case "on":
            case "enable":
                plugin.getPreferenceManager().setEnabled(player, true);
                player.sendMessage(ChatColor.GREEN + "Damage indicator enabled!");
                break;
            case "off":
            case "disable":
                plugin.getPreferenceManager().setEnabled(player, false);
                player.sendMessage(ChatColor.RED + "Damage indicator disabled!");
                break;
            default:
                player.sendMessage(ChatColor.RED + "Usage: /damageindicator [on|off]");
                break;
        }

        return true;
    }
}
