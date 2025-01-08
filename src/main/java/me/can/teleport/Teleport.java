package me.can.teleport;

import me.can.teleport.commands.teleportCommand;
import org.bukkit.plugin.java.JavaPlugin;

public final class Teleport extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().warning("Teleport plugini Yüklendi.");
        getCommand("teleport").setExecutor(new teleportCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
