package me.commandrod.eventtools.commands;

import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Dependency;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import me.commandrod.eventtools.api.commands.TargetedCommand;
import me.commandrod.eventtools.managers.ConfigManager;
import me.commandrod.eventtools.managers.MessageManager;
import me.commandrod.eventtools.utils.Replacement;
import net.kyori.adventure.text.Component;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

@CommandAlias("heal")
@CommandPermission("eventtools.heal")
public class HealCommand extends TargetedCommand {

    @Dependency
    private ConfigManager configManager;

    public void handle(Player sender, @Optional OnlinePlayer args) {
        Player player = args.getPlayer();

        AttributeInstance attribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        player.setHealth(attribute == null ? 20 : attribute.getBaseValue());
        player.setFoodLevel(20);
        List<PotionEffectType> potions = player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .toList();
        for (PotionEffectType potion : potions) {
            player.removePotionEffect(potion);
        }
        MessageManager messageManager = configManager.messageManager();

        Component msg = messageManager.PREFIX.append(
                configManager.getTranslatedMessage("heal", "You healed <yellow>%player%</yellow>!")
                        .replaceText(Replacement.builder()
                                .replace("%player%", player.getName())
                                .build())
        );
        if (messageManager.isEmpty(msg)) return;

        player.sendMessage(msg);
    }
}
