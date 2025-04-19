package me.can.teleport.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class tpToggleCommand implements CommandExecutor {

    private static HashMap<UUID, Boolean> isTpEnabled = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "You must be a player to use this command!");
            return false;
        }
        Player p = (Player) sender;
        UUID playerUUID = p.getUniqueId();

        boolean currentState = isTpEnabled.getOrDefault(playerUUID, true);

        boolean newState = !currentState;

        isTpEnabled.put(playerUUID, newState);

        if (newState) {
            p.sendMessage(ChatColor.GREEN + "Teleportunu Açtın.");
        } else {
            p.sendMessage(ChatColor.RED + "Teleportunu Kapattın.");
        }

        return true;
    }

    public static boolean isTeleportEnabled(UUID playerUUID) {
        return isTpEnabled.getOrDefault(playerUUID, true);
    }
}