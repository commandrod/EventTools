package me.commandrod.eventtools.managers;

import me.commandrod.eventtools.api.listener.OptionalListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public class ListenerManager {

    private final Plugin plugin;
    private final ConfigManager configManager;

    public ListenerManager(Plugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void register(OptionalListener optionalListener) {
        if (!configManager.getBoolean(optionalListener.getPath(), false)) return;
        register((Listener) optionalListener);
    }

    public void register(Listener listener) {
        Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
