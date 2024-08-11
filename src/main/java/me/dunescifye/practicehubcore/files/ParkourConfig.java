package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.Parkour;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Logger;

public class ParkourConfig {
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/Parkour/Parkour.yml"), plugin.getResource("gamemodes/Parkour/Parkour.yml"));
            //Copy World
            String worldName = config.getString("CopyWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Parkour gamemode disabled until fixed.");
                return;
            }
            Parkour.worldName = worldName;
            String[] coords = config.getString("SpawnLocation").split(" ");
            Parkour.spawnLocation = Arrays.stream(coords).mapToInt(Integer::parseInt).toArray();
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/Parkour/Parkour.yml");
            throw new RuntimeException(e);
        }
    }
}
