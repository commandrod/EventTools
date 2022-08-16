package me.commandrod.eventtools.listeners;

import me.commandrod.eventtools.api.listener.OptionalListener;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.SpawnManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerConnectionListener implements OptionalListener {

    private final ConfigManager configManager;
    private final SpawnManager spawnManager;

    public PlayerConnectionListener(ConfigManager configManager, SpawnManager spawnManager) {
        this.configManager = configManager;
        this.spawnManager = spawnManager;
    }

    @Override
    public String getPath() {
        return "handlers.connection.handle";
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        spawnManager.teleport(player);

        Component msg = configManager.getTranslatedMessage("connection.join", "<dark_gray>[<green>+</green>]</dark_gray> <green>%player%</green>")
                    .replaceText(Replacement.builder().replace("%player%", player.getName()).build());

        if (configManager.isEmpty(msg)) {
            event.joinMessage(Component.empty());
            return;
        }

        event.joinMessage(msg);
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        Component msg = configManager.getTranslatedMessage("connection.quit", "<dark_gray>[<red>-</red>]</dark_gray> <red>%player%</red>")
                .replaceText(Replacement.builder().replace("%player%", player.getName()).build());

        if (configManager.isEmpty(msg)) {
            event.quitMessage(Component.empty());
            return;
        }

        event.quitMessage(msg);
    }
}
