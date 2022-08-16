package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.EmptyCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;

@CommandAlias("reloadconfig|rlcf")
@CommandPermission("eventtools.reloadconfig")
public class ReloadConfigCommand extends EmptyCommand<CommandSender> {

    @Dependency
    private ConfigManager configManager;

    public void handle(CommandSender sender) {
        configManager.reload();

        Component msg = configManager.getMessages().CONFIG_PREFIX.append(
                configManager.getTranslatedMessage("reload-config", "Config has been successfully reloaded!" +
                        " <gray>[Handlers require a plugin reload to function in case they were changed]</gray>")
        );
        if (configManager.isEmpty(msg)) return;

        sender.sendMessage(msg);
    }
}
