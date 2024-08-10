package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class BridgeConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/Bridge/Bridge.yml"), plugin.getResource("gamemodes/Bridge/Bridge.yml"));
            //Copy World
            String worldName = config.getString("CopyWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Bridge gamemode disabled until fixed.");
                return;
            }
            Bridge.setCopyWorldName(worldName);
            //Messages
            Bridge.setStartingMessage(config.getString("Messages.Starting"));
            Bridge.setFallingMessage(config.getString("Messages.Falling"));
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/Bridge/Bridge.yml");
            throw new RuntimeException(e);
        }
    }
}
