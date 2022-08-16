package me.commandrod.eventtools.api.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;

public abstract class EmptyCommand<T extends CommandSender> extends BaseCommand {

    public abstract void handle(T sender);

    @Default
    public void emptyCommand(T sender, @Optional String[] args) {
        handle(sender);
    }
}
