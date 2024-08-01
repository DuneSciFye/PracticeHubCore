package me.dunescifye.practicehubcore;

import me.dunescifye.practicehubcore.commands.BridgeCommand;
import me.dunescifye.practicehubcore.listeners.PlayerBlockPlaceListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PracticeHubCore extends JavaPlugin {

    @Override
    public void onEnable() {
        Logger logger = Bukkit.getLogger();
        logger.info("PracticeHubCore Starting.");
        BridgeCommand.register();
        new PlayerBlockPlaceListener().playerBlockPlaceHandler(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
