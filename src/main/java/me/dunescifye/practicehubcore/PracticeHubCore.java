package me.dunescifye.practicehubcore;

import com.onarandombox.MultiverseCore.MultiverseCore;
import com.onarandombox.MultiverseCore.api.MVWorldManager;
import me.dunescifye.practicehubcore.commands.BridgeCommand;
import me.dunescifye.practicehubcore.listeners.PlayerBlockPlaceListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PracticeHubCore extends JavaPlugin {

    public static MVWorldManager worldManager;
    @Override
    public void onEnable() {
        Logger logger = Bukkit.getLogger();
        logger.info("PracticeHubCore Starting.");
        BridgeCommand.register(this);
        new PlayerBlockPlaceListener().playerBlockPlaceHandler(this);
        MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
        if (core == null) {
            logger.warning("PracticeHubCore could not hook into Multiverse-Core. This is required.");
        } else {
            worldManager = core.getMVWorldManager();
        }

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
