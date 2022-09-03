package me.commandrod.eventtools.api.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.entity.Player;

public abstract class TargetedCommand extends BaseCommand {

    public abstract void handle(Player sender, OnlinePlayer args);

    @Default
    public void targetedCommand(Player sender, @Optional OnlinePlayer target) {
        if (target == null) {
            handle(sender, new OnlinePlayer(sender));
            return;
        }
        handle(sender, target);
    }
}
