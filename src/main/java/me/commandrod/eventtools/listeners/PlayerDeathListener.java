package me.commandrod.eventtools.listeners;

import me.commandrod.eventtools.api.listener.OptionalListener;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.SpawnManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.apache.logging.log4j.Logger;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Optional;

public class PlayerDeathListener implements OptionalListener {

    private final ConfigManager configManager;
    private final SpawnManager spawnManager;
    private final Logger logger;

    public PlayerDeathListener(ConfigManager configManager, SpawnManager spawnManager, Logger logger) {
        this.configManager = configManager;
        this.spawnManager = spawnManager;
        this.logger = logger;
    }

    @Override
    public String getPath() {
        return "handlers.deaths.handle";
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDeath(PlayerDeathEvent event) {
        Player player = event.getPlayer();
        Player killer = player.getKiller();
        Component deathMessage;

        if (killer != null) {
            deathMessage = configManager.getTranslatedMessage("deaths.kill-message", "<red>%player% <white>has been eliminated by</white> %killer%</red>")
                    .replaceText(Replacement.builder()
                            .replace("%player%", player.getName())
                            .replace("%killer%", killer.getName())
                            .build());
        } else {
            deathMessage = configManager.getTranslatedMessage("deaths.death-message", "<red>%player% <white>has been eliminated</white></red>")
                    .replaceText(Replacement.builder()
                            .replace("%player%", player.getName())
                            .build());
        }

        event.deathMessage(deathMessage);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onRespawn(PlayerRespawnEvent event) {
        if (event.isAnchorSpawn() || event.isBedSpawn()) return;

        Component msg = configManager.getTranslatedMessage("deaths.respawn", "NONE");
        if (!configManager.messageManager().isEmpty(msg)) {
            event.getPlayer().sendMessage(msg);
        }

        Optional<Location> optionalLocation = spawnManager.getSpawn();
        if (optionalLocation.isEmpty()) {
            logger.error("Could not get spawn location.");
            return;
        }

        event.setRespawnLocation(optionalLocation.get());
    }
}
