package me.dunescifye.practicehubcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
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
        plugin = this;
        Logger logger = this.getLogger();
        logger.info("PracticeHubCore Starting.");
        Bridge.register(this);
        new Bridge().playerBlockPlaceHandler(this);

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

        //Files
        Config.setup();
    }

    public static PracticeHubCore getPlugin() {
        return plugin;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
