package me.can.teleport.commands;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.awt.*;

public class teleportCommand implements CommandExecutor{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)){
            sender.sendMessage(ChatColor.RED + "Kullanan oyuncu olmalı.");
            return false;
        }
        Player gonderici = (Player) sender;
        if (args.length <1) {
            gonderici.sendMessage(ChatColor.RED + "Kullanım Şekli: /teleport <oyuncu>");
            return false;
        }
        if (args.length>2){
            sender.sendMessage(ChatColor.RED+  "Kaç kişiyi birbirine teleportlucan orospu evladı");
            return false;
        }
        if (args.length<2 && args[0].equalsIgnoreCase(gonderici.getName())){
            sender.sendMessage( ChatColor.RED + "Kendine ışınlanamazsın.");
            return false;
        }
        handleTeleport(gonderici, args);

        return false;
    }
    public void handleTeleport(Player sender, String[] destination){
        if (destination.length>1) {
            Bukkit.getPlayer(destination[0]).teleport(Bukkit.getPlayer(destination[1]));
            sender.sendMessage( ChatColor.WHITE+ Bukkit.getPlayer((destination[0])).getName() + ChatColor.DARK_PURPLE + " Başarıyla " + ChatColor.WHITE+ Bukkit.getPlayer(destination[1]).getName() + ChatColor.DARK_PURPLE + " Adlı Oyuncuya Işınlandırıldı!");
        }
        else{
            sender.teleport(Bukkit.getPlayer(destination[0]));
            sender.sendMessage(Bukkit.getPlayer(destination[0]).getName() + ChatColor.DARK_PURPLE + " Adlı Oyuncuya Başarıyla Işınlandın!");

        }
    }

}
