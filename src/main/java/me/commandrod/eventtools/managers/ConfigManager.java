package me.commandrod.eventtools.managers;

import lombok.Getter;
import me.commandrod.eventtools.api.message.Messages;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class ConfigManager {

    private final Plugin plugin;
    private final MiniMessage miniMessage;
    private final PlainTextComponentSerializer plainTextComponentSerializer;

    @Getter private Messages messages;
    @Getter private FileConfiguration config;

    public ConfigManager(Plugin plugin, MiniMessage miniMessage, PlainTextComponentSerializer plainTextComponentSerializer) {
        this.plugin = plugin;
        this.miniMessage = miniMessage;
        this.plainTextComponentSerializer = plainTextComponentSerializer;

        this.config = plugin.getConfig();
        this.messages = new Messages(this);
    }

    public boolean getBoolean(String path, boolean def) {
        return config.getBoolean(path, def);
    }

    public String get(String path, String... def) {
        if (!config.isSet(path)) return String.join("", def) + " [DEFAULT]";
        return config.getString(path);
    }

    public Component getTranslated(String path, String... def) {
        String message = get(path, def);
        if (message.equals("NONE")) return Component.empty();
        return miniMessage.deserialize(get(path, def));
    }

    public String getMessage(String path, String... def) {
        return get("messages." + path, def);
    }

    public Component getTranslatedMessage(String path, String... def) {
        String message = getMessage(path, def);
        if (message.equals("NONE")) return Component.empty();
        return miniMessage.deserialize(message);
    }

    public boolean isEmpty(Component msg) {
        return plainTextComponentSerializer.serialize(msg).length() == 0;
    }

    public void save() {
        plugin.saveConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        this.config = plugin.getConfig();
        this.messages = new Messages(this);
    }
}
