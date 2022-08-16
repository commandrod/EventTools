package me.commandrod.eventtools.managers;

import lombok.Getter;
import net.kyori.adventure.audience.Audience;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ServerManager {

    private boolean eventRunning = false;
    private boolean chatMuted = false;
    private boolean PvP = false;
    private final Set<Audience> mutedChatViewers = new HashSet<>();

    public boolean toggleChat() {
        this.chatMuted = !chatMuted;
        return chatMuted;
    }

    public boolean togglePvP() {
        this.PvP = !PvP;
        return PvP;
    }

    public boolean toggleEvent() {
        this.eventRunning = !eventRunning;
        return eventRunning;
    }

    public boolean mutedChatViewer(Audience audience) {
        if (mutedChatViewers.contains(audience)) {
            mutedChatViewers.remove(audience);
            return false;
        }
        mutedChatViewers.add(audience);
        return true;
    }
}
