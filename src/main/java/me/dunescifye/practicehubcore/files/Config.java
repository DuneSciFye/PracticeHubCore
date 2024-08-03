package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.route.Route;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.io.File;
import java.io.IOException;

public class Config {
    public static Location spawn;
    public static void setup(PracticeHubCore plugin) {
        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), plugin.getResource("config.yml"));
            config.set(Route.from("Bridge.Spawn.World"), "world");
            config.set(Route.from("Bridge.Spawn.X"), "0");
            config.set(Route.from("Bridge.Spawn.Y"), "100");
            config.set(Route.from("Bridge.Spawn.Z"), "0");
            spawn = new Location(Bukkit.getWorld(config.getString("Bridge.Spawn.World")), config.getDouble("Bridge.Spawn.X"), config.getDouble("Bridge.Spawn.Y"), config.getDouble("Bridge.Spawn.Z"));
        } catch (
            IOException e) {
            plugin.getLogger().severe("Failed to load config.yml");
            throw new RuntimeException(e);
        }

    }

}
