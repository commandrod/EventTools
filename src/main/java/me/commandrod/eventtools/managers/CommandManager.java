package me.commandrod.eventtools.managers;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import org.bukkit.plugin.Plugin;

@Getter
public class CommandManager {

    private final PaperCommandManager commandManager;

    public CommandManager(Plugin plugin) {
        this.commandManager = new PaperCommandManager(plugin);
    }

    public void register(BaseCommand command) {
        this.commandManager.registerCommand(command);
    }
}
