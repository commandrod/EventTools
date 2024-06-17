package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.commandrod.eventtools.api.commands.TargetedCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.MessageManager;
import me.commandrod.eventtools.managers.SpawnManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

@CommandAlias("eventspawn|espawn")
@CommandPermission("eventtools.spawn")
public class SpawnCommand extends TargetedCommand {

    @Dependency
    private ConfigManager configManager;
    @Dependency
    private SpawnManager spawnManager;

    @CommandCompletion("normal|spectator @players @nothing")
    public void handle(Player sender, OnlinePlayer args) {
        Player player = args.getPlayer() == null ? sender : args.getPlayer();
        MessageManager messageManager = configManager.messageManager();

        if (!spawnManager.teleport(player)) {
            Component msg = messageManager.PREFIX.append(
                    configManager.getTranslatedMessage("spawn.not-found", "<red>Could not find the spawn!</red>")
            );
            if (messageManager.isEmpty(msg)) return;

            sender.sendMessage(msg);
            return;
        }

        Component msg = messageManager.PREFIX.append(
                configManager.getTranslatedMessage("spawn.teleported", "<dark_aqua>Teleported <aqua>%player%</aqua> to spawn!</dark_aqua>")
                        .replaceText(Replacement.builder().replace("%player%", player.getName()).build())
        );
        if (messageManager.isEmpty(msg)) return;

        sender.sendMessage(msg);
    }
}
