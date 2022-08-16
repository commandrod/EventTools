package me.commandrod.eventtools.managers;

import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SpawnManager {

    private final ConfigManager configManager;
    private final String path;

    public SpawnManager(ConfigManager configManager, Logger logger) {
        this.configManager = configManager;
        this.path = "handlers.spawn.spawn-location";

        if (getSpawn().isPresent()) return;
        logger.error("Could not get the spawn location!");
    }

    public Optional<Location> getSpawn() {
        return Optional.ofNullable(configManager.getConfig().getLocation(path));
    }

    public void setSpawn(@Nullable Location location) {
        if (location == null) return;
        configManager.getConfig().set(path, location);
    }

    public boolean teleport(Player player) {
        Optional<Location> spawn = getSpawn();
        spawn.ifPresent(player::teleportAsync);
        return spawn.isPresent();
    }
}
