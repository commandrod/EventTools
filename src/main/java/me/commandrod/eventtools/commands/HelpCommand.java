package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import me.commandrod.eventtools.api.commands.EmptyCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@CommandAlias("event|eventtools|eventhelp")
@CommandPermission("eventtools.help")
public class HelpCommand extends EmptyCommand<CommandSender> {

    @Dependency
    private MiniMessage miniMessage;

    private final Set<HelpCommandInformation> commands = new HashSet<>(Arrays.asList(
            new HelpCommandInformation("eventhelp", "Shows this message"),
            new HelpCommandInformation("event", "Toggles the event"),
            new HelpCommandInformation("heal [player]", "Heals a player"),
            new HelpCommandInformation("rlcf", "Reloads the config"),
            new HelpCommandInformation("chat", "Toggles the chat"),
            new HelpCommandInformation("pvp", "Toggles the pvp"),
            new HelpCommandInformation("tmm", "Toggles visibility of messages during muted chat <gray>[FOR STAFF ONLY]</gray>"),
            new HelpCommandInformation("espawn [spawnName] [player]", "Teleports to an event spawn"),
            new HelpCommandInformation("esetspawn <spawnName>", "Sets a location for an event spawn")
    ));

    public void handle(CommandSender sender) {
        TextComponent.Builder builder = Component.text();

        builder.append(miniMessage.deserialize(

                """
                               <aqua>Commands</aqua>
                        ﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊﹊
                        """
        ));

        for (HelpCommandInformation information : commands) {
            builder.append(miniMessage.deserialize("<gray> - </gray><aqua>" + information.name + "</aqua> <gray>-</gray> " + information.description + "\n"));
        }

        sender.sendMessage(builder.build());
    }

    private record HelpCommandInformation(String name, String description) {
    }
}
