package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.FallClutch;
import me.dunescifye.practicehubcore.gamemodes.LilyPadBridge;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class LilyPadBridgeConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/LilyPadBridge/LilyPadBridge.yml"), plugin.getResource("gamemodes/LilyPadBridge/LilyPadBridge.yml"));
            //Copy World
            String worldName = config.getString("CopyWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Lily Pad Bridge gamemode disabled until fixed.");
            } else {
                LilyPadBridge.copyWorld = worldName;
            }
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/LilyPadBridge/LilyPadBridge.yml");
            throw new RuntimeException(e);
        }
    }

}
