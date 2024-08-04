package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class PortalBuildConfig {

    public static World portalBuildWorld = null;
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/PortalBuild/PortalBuild.yml"), plugin.getResource("gamemodes/PortalBuild/PortalBuild.yml"));
            //Copy World
            String worldName = config.getString("BuildWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Portal Build gamemode disabled until fixed.");
            } else {
                portalBuildWorld = world;
            }

        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/PortalBuild.yml");
            throw new RuntimeException(e);
        }

        File schematics = new File(plugin.getDataFolder(), "gamemodes/PortalBuild/Schematics");

        // Create the directory if it doesn't exist
        if (!schematics.exists()) {
            schematics.mkdir();
        }

    }


}
