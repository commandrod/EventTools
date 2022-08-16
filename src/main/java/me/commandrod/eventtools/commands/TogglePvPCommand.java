package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.ArgsCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.ServerManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("togglepvp|pvp")
@CommandPermission("eventtools.pvp")
public class TogglePvPCommand extends ArgsCommand<CommandSender> {

    @Dependency private ServerManager serverManager;
    @Dependency private ConfigManager configManager;

    @CommandCompletion("-s @nothing")
    public void handle(CommandSender sender, String[] args) {
        boolean isPvP = serverManager.togglePvP();
        String state = isPvP ? "<green>ENABLED</green>" : "<red>DISABLED</red>";

        Component msg = configManager.getMessages().PREFIX.append(
                configManager.getTranslatedMessage(isPvP ? "pvp.enabled" : "pvp.disabled", "PvP has been %state%!")
                        .replaceText(Replacement.builder().replace("%state%", state).build())
        );

        if (args.length != 0 && args[0].equalsIgnoreCase("-s")) {
            sender.sendMessage(msg.append(configManager.getMessages().SILENT));
            return;
        }

        if (configManager.isEmpty(msg)) return;
        Bukkit.broadcast(msg);
    }
}
