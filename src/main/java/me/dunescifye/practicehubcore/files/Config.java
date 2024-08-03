package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;

import java.io.File;
import java.io.IOException;

public class Config {

    public static void setup(PracticeHubCore plugin) {
        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));

            config.
        } catch (
            IOException e) {
            plugin.getLogger().severe("Failed to load config.yml");
            throw new RuntimeException(e);
        }

    }

}
