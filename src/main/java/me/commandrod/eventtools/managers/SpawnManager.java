package me.commandrod.eventtools.managers;

import lombok.Getter;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class SpawnManager {

    @Getter
    private Optional<Location> spawn;

    public SpawnManager(ConfigManager configManager, Logger logger) {
        this.spawn = Optional.ofNullable(configManager.getConfig().getLocation("handlers.spawn.spawn-location"));

        if (spawn.isPresent()) return;
        logger.error("Could not get the spawn location!");
    }

    public void setSpawn(@Nullable Location location) {
        this.spawn = Optional.ofNullable(location);
    }

    public boolean teleport(Player player) {
        spawn.ifPresent(player::teleportAsync);
        return spawn.isPresent();
    }
}
