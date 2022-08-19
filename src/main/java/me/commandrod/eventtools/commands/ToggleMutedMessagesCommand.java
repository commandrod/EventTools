package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.EmptyCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.MessageManager;
import me.commandrod.eventtools.managers.ServerManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@CommandAlias("togglemutedmessages|tmm")
@CommandPermission("eventtools.togglemutedmessages")
public class ToggleMutedMessagesCommand extends EmptyCommand<CommandSender> {

    @Dependency
    private ServerManager serverManager;
    @Dependency
    private ConfigManager configManager;

    public void handle(CommandSender sender) {
        boolean isViewer = serverManager.mutedChatViewer(sender);
        String path = isViewer
                ? "chat.add-muted-chat-viewer"
                : "chat.remove-muted-chat-viewer";
        MessageManager messageManager = configManager.messageManager();

        Component msg = messageManager.CHAT_PREFIX.append(
                configManager.getTranslatedMessage(path, "Now %state% messages while chat is muted!")
                        .replaceText(Replacement.builder()
                                .replace("%state%", isViewer ? "<green>VIEWING</green>" : "<red>HIDING</red>")
                                .build()
                        )
        );
        if (messageManager.isEmpty(msg)) return;

        sender.sendMessage(msg);
    }
}



