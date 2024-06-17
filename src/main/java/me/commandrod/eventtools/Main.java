package me.commandrod.eventtools;

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
        commandManager.registerDependency(ServerManager.class, serverManager);
        commandManager.registerDependency(ConfigManager.class, configManager);
        commandManager.registerDependency(ListenerManager.class, listenerManager);
        commandManager.registerDependency(SpawnManager.class, spawnManager);
        commandManager.registerDependency(MiniMessage.class, miniMessage);
    }

    public void registerCommands() {
        commandManager.registerCommand(new HelpCommand());
        commandManager.registerCommand(new ReloadConfigCommand());
        commandManager.registerCommand(new HealCommand());
        commandManager.registerCommand(new ToggleChatCommand());
        commandManager.registerCommand(new ToggleMutedMessagesCommand());
        commandManager.registerCommand(new TogglePvPCommand());
        commandManager.registerCommand(new SetSpawnCommand());
        commandManager.registerCommand(new SpawnCommand());
    }
}
