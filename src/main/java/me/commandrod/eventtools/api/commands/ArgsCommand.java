package me.commandrod.eventtools.api.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Optional;
import org.bukkit.command.CommandSender;

public abstract class ArgsCommand<T extends CommandSender> extends BaseCommand {

    public abstract void handle(T sender, String... args);

    @Default
    public void argsCommand(T sender, @Optional String[] args) {
        handle(sender, args == null ? new String[]{} : args);
    }
}
