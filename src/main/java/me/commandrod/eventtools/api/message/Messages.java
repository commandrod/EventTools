package me.commandrod.eventtools.api.message;

import me.commandrod.eventtools.managers.ConfigManager;
import net.kyori.adventure.text.Component;

public class Messages {

    public Component PREFIX;
    public Component CHAT_PREFIX;
    public Component CONFIG_PREFIX;
    public Component SILENT;

    public Messages(ConfigManager configManager) {
        String prefix = "constants.";
        PREFIX = configManager.getTranslated(prefix + "main", "<dark_aqua>Event</dark_aqua><dark_gray> > </dark_gray><gray>");
        CHAT_PREFIX = configManager.getTranslated(prefix + "chat", "<red><b>Chat</b></red><dark_gray> > </dark_gray><gray>");
        CONFIG_PREFIX = configManager.getTranslated(prefix + "config", "<yellow>Config</yellow><dark_gray> > </dark_gray><gray>");
        SILENT = configManager.getTranslated(prefix + "silent", " <gray>[SILENT]</gray>");
    }
}
