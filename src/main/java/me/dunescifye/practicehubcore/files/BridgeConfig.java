package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;

import java.io.File;
import java.io.IOException;

public class BridgeConfig {

    public static

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/bridge.yml"), plugin.getResource("bridge.yml"));
            config.get
        } catch (
            IOException e) {
            plugin.getLogger().severe("Failed to load file gamemodes/bridge.yml");
            throw new RuntimeException(e);
        }
    }
}
