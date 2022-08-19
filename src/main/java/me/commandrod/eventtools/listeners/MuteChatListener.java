package me.commandrod.eventtools.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.ServerManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public record MuteChatListener(ConfigManager configManager,
                               ServerManager serverManager) implements Listener {

    @EventHandler
    public void onChat(AsyncChatEvent event) {
        if (!serverManager.isChatMuted()) return;

        Player player = event.getPlayer();

        if (player.hasPermission("eventtools.mutechat.bypass")) return;
        Component msg = configManager.messageManager().CHAT_PREFIX
                .append(configManager.getTranslatedMessage("chat.muted-chat-message",
                                "<gold>%player%</gold><gray>:</gray> <yellow>%message%</yellow>")
                        .replaceText(Replacement.builder()
                                .replace("%player%", player.getName())
                                .replace("%message%", event.message())
                                .build()
                        )
                );
        if (configManager.messageManager().isEmpty(msg)) return;

        for (Audience audience : serverManager.getMutedChatViewers()) {
            audience.sendMessage(msg);
        }

        event.setCancelled(true);
    }
}
