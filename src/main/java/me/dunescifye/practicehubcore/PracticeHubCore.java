package me.dunescifye.practicehubcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import me.dunescifye.practicehubcore.commands.*;
import me.dunescifye.practicehubcore.files.*;
import me.dunescifye.practicehubcore.gamemodes.*;
import me.dunescifye.practicehubcore.listeners.*;
import me.dunescifye.practicehubcore.placeholders.Placeholders;
import me.dunescifye.practicehubcore.utils.ClicksPerSecond;
import me.dunescifye.practicehubcore.utils.Database;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Logger;

public final class PracticeHubCore extends JavaPlugin {

    public static MVWorldManager worldManager;
    private static PracticeHubCore plugin;
    public static boolean placeholderAPIEnabled;
    public static HashMap<UUID, Gamemode> gamemodes = new HashMap<>();

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(this));
    }

    @Override
    public void onEnable() {
        //Logger
        plugin = this;
        Logger logger = this.getLogger();
        logger.info("PracticeHubCore Starting.");

        //Setup
        setupFiles();
        CommandAPI.onEnable();
        //files before commands
        setupCommands();
        setupListeners();

        //Multiverse
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (core == null) {
            logger.warning("PracticeHubCore could not hook into Multiverse-Core. This is required.");
            Bukkit.getPluginManager().disablePlugin(this);
        } else {
            worldManager = core.getMVWorldManager();
        }

        //PlaceholderAPI
        if (placeholderAPIEnabled && Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders().register();
            logger.info("Found PlaceholderAPI. Enabling Placeholders.");
        }

        //CPS
        new ClicksPerSecond().setup(this);
        logger.info("PracticeHubCore Started.");

        Database.createDataSource();
    }

    public static PracticeHubCore getPlugin() {
        return plugin;
    }

    private void setupCommands() {
        BridgeCommand.register();
        MainCommand.register();
        MiscCommands.register();
        BridgeCommand.register();
        PortalBuildCommand.register();
        BowAimCommand.register();
        FallClutchCommand.register();
        LilyPadBridgeCommand.register();
        ParkourCommand.register();
        ShieldPVPCommand.register();
    }

    private void setupListeners() {
        new BlockPlaceListener().playerBlockPlaceHandler(this);
        new PortalBuild().portalCreateHandler(this);
        //new BowAim().registerEvents(this);
        new FallClutch().registerEvents(this);
        new BlockBreakListener().registerEvents(this);
        new PlayerQuitListener().registerEvents(this);
        new PlayerDeathListener().registerEvents(this);
        new PlayerJoinListener().registerEvents(this);
    }

    public void setupFiles() {
        BowBoost.setup(this);
        Config.setup();
        BridgeConfig.setup();
        PortalBuildConfig.setup();
        Messages.setup();
        //BowBoostConfig.setup();
        //BowAimConfig.setup();
        FallClutchConfig.setup();
        LilyPadBridgeConfig.setup();
        ParkourConfig.setup();
        ShieldPVPConfig.setup();
    }

    @Override
    public void onDisable() {
        Logger logger = this.getLogger();
        logger.info("PracticeHubCore Stopping.");
        CommandAPI.unregister("bowaim");
        CommandAPI.unregister("bowboost");
        CommandAPI.unregister("bridge");
        CommandAPI.unregister("fallclutch");
        CommandAPI.unregister("lilypadbridge");
        CommandAPI.unregister("parkour");
        CommandAPI.unregister("portalbuild");
        CommandAPI.unregister("shieldpvp");
        CommandAPI.unregister("ping");
        CommandAPI.unregister("spawn");
        CommandAPI.onDisable();
    }
}
