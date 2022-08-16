package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.EmptyCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.SpawnManager;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@CommandAlias("eventsetspawn|esetspawn")
@CommandPermission("eventtools.setspawn")
public class SetSpawnCommand extends EmptyCommand<Player> {

    @Dependency
    private ConfigManager configManager;
    @Dependency
    private SpawnManager spawnManager;

    @CommandCompletion("normal|spectator @nothing")
    public void handle(Player sender) {
        spawnManager.setSpawn(sender.getLocation());

        Component msg = configManager.getMessages().CONFIG_PREFIX.append(
                configManager.getTranslatedMessage("spawn.set", "<yellow>Successfully set the spawn location!</yellow>")
        );
        if (configManager.isEmpty(msg)) return;

        sender.sendMessage(msg);
    }
}
