package me.dunescifye.practicehubcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.dunescifye.practicehubcore.commands.*;
import me.dunescifye.practicehubcore.files.*;
import me.dunescifye.practicehubcore.gamemodes.*;
import me.dunescifye.practicehubcore.listeners.BlockBreakListener;
import me.dunescifye.practicehubcore.listeners.BlockPlaceListener;
import me.dunescifye.practicehubcore.listeners.PlayerQuitListener;
import me.dunescifye.practicehubcore.placeholders.Placeholders;
import me.dunescifye.practicehubcore.utils.ClicksPerSecond;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PracticeHubCore extends JavaPlugin {

    public static MVWorldManager worldManager;
    private static PracticeHubCore plugin;
    public static boolean placeholderAPIEnabled;

    @Override
    public void onEnable() {
        //Logger
        plugin = this;
        Logger logger = this.getLogger();
        logger.info("PracticeHubCore Starting.");

        //Setup
        setupCommands();
        setupListeners();
        setupFiles();

        //Multiverse
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (core == null) {
            logger.warning("PracticeHubCore could not hook into Multiverse-Core. This is required.");
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
        BowBoostCommand.register();
        BowAimCommand.register();
        FallClutchCommand.register();
        LilyPadBridgeCommand.register();
        ParkourCommand.register();
    }

    private void setupListeners() {
        new BlockPlaceListener().playerBlockPlaceHandler(this);
        new PortalBuild().portalCreateHandler(this);
        new BowBoost().registerEvents(this);
        new BowAim().registerEvents(this);
        new FallClutch().registerEvents(this);
        new BlockBreakListener().registerEvents(this);
        new PlayerQuitListener().registerEvents(this);
    }

    public static void setupFiles() {
        Config.setup();
        BridgeConfig.setup();
        PortalBuildConfig.setup();
        BowBoostConfig.setup();
        BowAimConfig.setup();
        FallClutchConfig.setup();
        LilyPadBridgeConfig.setup();
        ParkourConfig.setup();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("PracticeHubCore Stopping.");
    }
}
