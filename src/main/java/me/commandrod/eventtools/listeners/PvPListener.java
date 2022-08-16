package me.commandrod.eventtools.listeners;

import me.commandrod.eventtools.api.listener.OptionalListener;
import me.commandrod.eventtools.managers.ServerManager;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PvPListener implements OptionalListener {

    private final ServerManager serverManager;

    public PvPListener(ServerManager serverManager) {
        this.serverManager = serverManager;
    }

    @Override
    public String getPath() {
        return "handlers.pvp.handle";
    }

    private final Set<EntityDamageEvent.DamageCause> blacklistedCauses = new HashSet<>(Arrays.asList(
            EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK,
            EntityDamageEvent.DamageCause.ENTITY_ATTACK,
            EntityDamageEvent.DamageCause.LAVA,
            EntityDamageEvent.DamageCause.CUSTOM,
            EntityDamageEvent.DamageCause.VOID,
            EntityDamageEvent.DamageCause.SUICIDE,
            EntityDamageEvent.DamageCause.HOT_FLOOR,
            EntityDamageEvent.DamageCause.FALLING_BLOCK,
            EntityDamageEvent.DamageCause.MAGIC,
            EntityDamageEvent.DamageCause.POISON,
            EntityDamageEvent.DamageCause.STARVATION
    ));

    @EventHandler
    public void onPunch(EntityDamageByEntityEvent event) {
        if (serverManager.isPvP()) return;
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (!event.getDamager().getType().equals(EntityType.PLAYER)) return;
        event.setCancelled(true);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event) {
        if (serverManager.isPvP()) return;
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        if (blacklistedCauses.contains(event.getCause())) return;
        event.setCancelled(true);
    }
}
