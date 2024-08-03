package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class PortalBuildConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/PortalBuild.yml"), plugin.getResource("PortalBuild.yml"));
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/PortalBuild.yml");
            throw new RuntimeException(e);
        }
    }


}
