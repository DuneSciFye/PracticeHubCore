package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class Config {
    public static Location spawn;
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));
            config.set("Spawn.World", "world");
            config.set("Spawn.X", "0");
            config.set("Spawn.Y", "100");
            config.set("Spawn.Z", "0");
            spawn = new Location(Bukkit.getWorld(config.getString("Spawn.World")), config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"));
        } catch (
            IOException e) {
            plugin.getLogger().severe("Failed to load config.yml");
            throw new RuntimeException(e);
        }

    }

}
