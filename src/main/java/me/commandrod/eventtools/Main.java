package me.commandrod.eventtools;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import me.commandrod.eventtools.commands.*;
import me.commandrod.eventtools.listeners.MuteChatListener;
import me.commandrod.eventtools.listeners.PlayerConnectionListener;
import me.commandrod.eventtools.listeners.PlayerDeathListener;
import me.commandrod.eventtools.listeners.PvPListener;
import me.commandrod.eventtools.managers.*;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
public final class Main extends JavaPlugin {

    private ServerManager serverManager;
    private ConfigManager configManager;
    private CommandManager commandManager;
    private ListenerManager listenerManager;
    private SpawnManager spawnManager;

    @Getter
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private final PlainTextComponentSerializer plainTextComponentSerializer = PlainTextComponentSerializer.builder().build();

    @Override
    public void onEnable() {
        saveDefaultConfig();

        this.serverManager = new ServerManager();
        this.configManager = new ConfigManager(this, miniMessage, plainTextComponentSerializer);
        this.commandManager = new CommandManager(this);
        this.listenerManager = new ListenerManager(this, configManager);
        this.spawnManager = new SpawnManager(configManager, this.getLog4JLogger());

        registerListeners();
        registerDependencies();
        registerCommands();
    }

    public void registerListeners() {
        listenerManager.register(new MuteChatListener(configManager, serverManager));
        listenerManager.register(new PvPListener(serverManager));
        listenerManager.register(new PlayerDeathListener(configManager, spawnManager, this.getLog4JLogger()));
        listenerManager.register(new PlayerConnectionListener(configManager, spawnManager));
    }

    public void registerDependencies() {
        PaperCommandManager paperCommandManager = commandManager.getCommandManager();
        paperCommandManager.registerDependency(ServerManager.class, serverManager);
        paperCommandManager.registerDependency(ConfigManager.class, configManager);
        paperCommandManager.registerDependency(ListenerManager.class, listenerManager);
        paperCommandManager.registerDependency(SpawnManager.class, spawnManager);
        paperCommandManager.registerDependency(MiniMessage.class, miniMessage);
    }

    public void registerCommands() {
        this.commandManager.register(new HelpCommand());
        this.commandManager.register(new ReloadConfigCommand());
        this.commandManager.register(new HealCommand());
        this.commandManager.register(new ToggleChatCommand());
        this.commandManager.register(new ToggleMutedMessagesCommand());
        this.commandManager.register(new TogglePvPCommand());
        this.commandManager.register(new SetSpawnCommand());
        this.commandManager.register(new SpawnCommand());
    }
}
