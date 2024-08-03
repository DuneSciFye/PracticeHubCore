package me.dunescifye.practicehubcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.dunescifye.practicehubcore.commands.BridgeCommand;
import me.dunescifye.practicehubcore.commands.MainCommand;
import me.dunescifye.practicehubcore.commands.MiscCommands;
import me.dunescifye.practicehubcore.commands.PortalBuildCommand;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import me.dunescifye.practicehubcore.placeholders.Placeholders;
import me.dunescifye.practicehubcore.utils.ClicksPerSecond;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PracticeHubCore extends JavaPlugin {

    public static MVWorldManager worldManager;
    private static PracticeHubCore plugin;

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
        if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
            new Placeholders().register();
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
    }

    private void setupListeners() {
        new Bridge().playerBlockPlaceHandler(this);
    }

    private void setupFiles() {
        Config.setup();
        BridgeConfig.setup();
    }

    @Override
    public void onDisable() {
        this.getLogger().info("PracticeHubCore Stopping.");
    }
}
