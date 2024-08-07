package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.FallClutch;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class FallClutchConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/FallClutch/FallClutch.yml"), plugin.getResource("gamemodes/FallClutch/FallClutch.yml"));
            //Copy World
            String worldName = config.getString("World");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Fall Clutch gamemode disabled until fixed.");
            } else {
                FallClutch.world = world;
            }
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/FallClutch/FallClutch.yml");
            throw new RuntimeException(e);
        }
    }

}
