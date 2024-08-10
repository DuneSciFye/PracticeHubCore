package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ShieldPVPConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/ShieldPVP/config.yml"), plugin.getResource("gamemodes/ShieldPVP/config.yml"));
            ShieldPVP.setEnabled(config.getBoolean("Enabled"));
            config.addComment("Set to false to disable this gamemode.");
        } catch (
            IOException e) {
            logger.severe("An error occurred while loading gamemodes/ShieldPVP/config.yml");
            throw new RuntimeException(e);
        }
    }

}
