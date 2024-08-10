package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.commands.MiscCommands;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class Config {
    public static Location spawn;
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "config.yml"), Objects.requireNonNull(plugin.getResource("config.yml")));

            //Spawn
            World world = Bukkit.getWorld(config.getString("Spawn.World"));
            if (world == null) {
                plugin.getLogger().warning("Spawn world is invalid. Check config.yml");
                String defaultWorld = Bukkit.getWorlds().get(0).getName();
                config.set("Spawn.World", defaultWorld);
                plugin.getLogger().warning("Setting spawn to \"" + defaultWorld + "\"");
            } else {
                spawn = new Location(Bukkit.getWorld(config.getString("Spawn.World")), config.getDouble("Spawn.X"), config.getDouble("Spawn.Y"), config.getDouble("Spawn.Z"));
            }

            //Hooks
            PracticeHubCore.placeholderAPIEnabled = config.getBoolean("Hooks.PlaceholderAPI");

            //Commands
            MiscCommands.setPingCommandEnabled(config.getBoolean("Commands.Ping"));
            MiscCommands.setSpawnCommandEnabled(config.getBoolean("Commands.Spawn"));

        } catch (
            IOException e) {
            plugin.getLogger().severe("Failed to load file config.yml");
            throw new RuntimeException(e);
        }

    }

}
