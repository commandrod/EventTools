package me.commandrod.eventtools.managers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;

public class MessageManager {

    private final PlainTextComponentSerializer plainTextComponentSerializer;

    public Component PREFIX;
    public Component CHAT_PREFIX;
    public Component CONFIG_PREFIX;
    public Component SILENT;

    public MessageManager(ConfigManager configManager, PlainTextComponentSerializer plainTextComponentSerializer) {
        this.plainTextComponentSerializer = plainTextComponentSerializer;

        String prefix = "constants.";
        PREFIX = configManager.getTranslated(prefix + "main", "<dark_aqua>Event</dark_aqua><dark_gray> > </dark_gray><gray>");
        CHAT_PREFIX = configManager.getTranslated(prefix + "chat", "<red><b>Chat</b></red><dark_gray> > </dark_gray><gray>");
        CONFIG_PREFIX = configManager.getTranslated(prefix + "config", "<yellow>Config</yellow><dark_gray> > </dark_gray><gray>");
        SILENT = configManager.getTranslated(prefix + "silent", " <gray>[SILENT]</gray>");
    }

    public boolean isEmpty(Component component) {
        return plainTextComponentSerializer.serialize(component).length() == 0;
    }
}
