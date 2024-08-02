package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;

import java.io.File;

public class Config {

    public static void setup(PracticeHubCore plugin) {
        YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), getRe)
    }

}
