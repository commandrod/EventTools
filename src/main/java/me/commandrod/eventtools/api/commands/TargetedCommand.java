package me.commandrod.eventtools.api.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public abstract class TargetedCommand<T extends CommandSender> extends BaseCommand {

    public abstract void handle(T sender, OnlinePlayer args);

    @Default
    public void targetedCommand(T sender, @Optional OnlinePlayer target) {
        if (sender == null) return;
        if (target == null) {
            if (!(sender instanceof Player player)) {
                sender.sendMessage("EventTools > You must provide a player in order to execute this command.");
                return;
            }
            handle(sender, new OnlinePlayer(player));
            return;
        }
        handle(sender, target);
    }
}
