package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.ArgsCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.MessageManager;
import me.commandrod.eventtools.managers.ServerManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("chat|mutechat|togglechat")
@CommandPermission("eventtools.mutechat")
public class ToggleChatCommand extends ArgsCommand<CommandSender> {

    @Dependency
    private ConfigManager configManager;
    @Dependency
    private ServerManager serverManager;

    @CommandCompletion("-s @nothing")
    public void handle(CommandSender sender, String[] args) {
        boolean isMuted = serverManager.toggleChat();
        String state = isMuted ? "<red>MUTED</red>" : "<green>UNMUTED</green>";
        MessageManager messageManager = configManager.messageManager();

        Component msg = messageManager.PREFIX.append(
                configManager.getTranslatedMessage(isMuted ? "chat.muted" : "chat.unmuted", "Chat has been %state%!")
                        .replaceText(Replacement.builder().replace("%state%", state).build())
        );

        if (args.length != 0 && args[0].equalsIgnoreCase("-s")) {
            sender.sendMessage(msg.append(messageManager.SILENT));
            return;
        }

        if (messageManager.isEmpty(msg)) return;
        Bukkit.broadcast(msg);
    }
}



