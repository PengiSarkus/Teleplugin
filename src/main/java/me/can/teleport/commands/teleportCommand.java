package me.can.teleport.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class teleportCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Kullanan oyuncu olmalı.");
            return false;
        }
        Player player = (Player) sender;

        if (args.length == 1) {
            String targetName = args[0];
            Player targetPlayer = Bukkit.getPlayer(targetName);

            if (targetPlayer == null) {
                player.sendMessage(ChatColor.RED + "Oyuncu '" + targetName + "' bulunamadı veya çevrimdışı.");
                return false;
            }

            if (targetPlayer.equals(player)) {
                player.sendMessage(ChatColor.RED + "Kendine ışınlanamazsın.");
                return false;
            }

            if (!tpToggleCommand.isTeleportEnabled(targetPlayer.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Işınlanmaya çalıştığınız oyuncunun teleportu kapalı!");
                targetPlayer.sendMessage(ChatColor.YELLOW + player.getName() + " adlı oyuncu size ışınlanmaya çalıştı fakat teleportunuz kapalı!");
                return false;
            }
            if (!tpToggleCommand.isTeleportEnabled(player.getUniqueId())) {
                player.sendMessage(ChatColor.RED + "Teleportun Kapalı!");
                return false;
            }

            handleTeleport(player, targetPlayer);
            player.sendMessage(ChatColor.DARK_PURPLE + targetPlayer.getName() + " adlı oyuncuya başarıyla ışınlandın!");
            return true;

        } else if (args.length == 2) {
            String player1Name = args[0];
            String player2Name = args[1];

            Player targetPlayer1 = Bukkit.getPlayer(player1Name);
            Player targetPlayer2 = Bukkit.getPlayer(player2Name);

            if (targetPlayer1 == null || targetPlayer2 == null) {
                String offlinePlayerName = (targetPlayer1 == null) ? player1Name : player2Name;
                player.sendMessage(ChatColor.RED + "Oyuncu '" + offlinePlayerName + "' bulunamadı veya çevrimdışı.");
                return false;
            }

            boolean player1Enabled = tpToggleCommand.isTeleportEnabled(targetPlayer1.getUniqueId());
            boolean player2Enabled = tpToggleCommand.isTeleportEnabled(targetPlayer2.getUniqueId());

            if (!player1Enabled || !player2Enabled) {
                String message = ChatColor.RED + "Işınlama başarısız. ";
                if (!player1Enabled && !player2Enabled) {
                    message += targetPlayer1.getName() + " ve " + targetPlayer2.getName() + " adlı oyuncuların teleportu kapalı!";
                } else if (!player1Enabled) {
                    message += targetPlayer1.getName() + " adlı oyuncunun teleportu kapalı!";
                } else {
                    message += targetPlayer2.getName() + " adlı oyuncunun teleportu kapalı!";
                }
                player.sendMessage(message);

                if (!player1Enabled) {
                    targetPlayer1.sendMessage(ChatColor.YELLOW + player.getName() + " adlı oyuncu sizi birine ışınlamaya çalıştı fakat teleportunuz kapalı!");
                }
                if (!player2Enabled && !targetPlayer1.equals(targetPlayer2)) {
                    targetPlayer2.sendMessage(ChatColor.YELLOW + player.getName() + " adlı oyuncu sizi birine ışınlamaya çalıştı fakat teleportunuz kapalı!");
                }

                return false;
            }

            handleTeleport(targetPlayer1, targetPlayer2);
            player.sendMessage(ChatColor.WHITE + targetPlayer1.getName() + ChatColor.DARK_PURPLE + " başarıyla " + ChatColor.WHITE + targetPlayer2.getName() + ChatColor.DARK_PURPLE + " adlı oyuncuya ışınlandırıldı!");
            return true;

        } else {
            player.sendMessage(ChatColor.RED + "Kullanım Şekli: /teleport <oyuncu> veya /teleport <oyuncu1> <oyuncu2>");
            return false;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 2) {
            return new ArrayList<>();
        }

        String currentArg = args.length == 0 ? "" : args[args.length - 1].toLowerCase();

        List<String> playerNames = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            playerNames.add(onlinePlayer.getName());
        }

        List<String> suggestions = new ArrayList<>();
        for (String playerName : playerNames) {
            if (playerName.toLowerCase().startsWith(currentArg)) {
                suggestions.add(playerName);
            }
        }

        Collections.sort(suggestions);

        return suggestions;
    }

    private void handleTeleport(Player teleporter, Player destination) {
        teleporter.teleport(destination);
    }
}