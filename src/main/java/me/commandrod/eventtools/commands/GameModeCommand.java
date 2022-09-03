package me.commandrod.eventtools.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

public class GameModeCommand extends BaseCommand {

    @Dependency private ConfigManager configManager;

    @Default
    public void handle(Player sender, GameMode gameMode, @Optional OnlinePlayer args) {
        Player player = args == null ? sender : args.getPlayer();

        player.setGameMode(gameMode);

        Component msg = configManager.getMessages().PREFIX.append(
                configManager.getTranslatedMessage("gamemode.change", "<gray>Set <yellow>%player%</yellow>'s gamemode to <yellow>%gamemode%!</gray>")
                        .replaceText(Replacement.builder()
                                .replace("%player%", player.getName())
                                .build())
                        .replaceText(Replacement.builder()
                                .replace("%gamemode%", gameMode.name().toLowerCase())
                                .build())
        );
        if (configManager.isEmpty(msg)) return;

        player.sendMessage(msg);
    }
}
