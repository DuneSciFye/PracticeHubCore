package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP.ShieldPVP;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class ShieldPVPConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/ShieldPVP/config.yml"), plugin.getResource("gamemodes/ShieldPVP/config.yml"));
            boolean enabled = config.getBoolean("Enabled");
            config.addComment("Set to false to disable this gamemode.");
            ShieldPVP.setEnabled(enabled);

            if (!enabled) return;
            String worldName = config.getString("CopyWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("Shield PVP World is invalid! Gamemode will be disabled until fixed.");
                ShieldPVP.setEnabled(false);
                return;
            }
            ShieldPVP.setWorldName(worldName);
            ShieldPVP.setWorld(world);
            ShieldPVP.setCommandAliases(config.getStringList("CommandAliases").toArray(new String[0]));


        } catch (
            IOException e) {
            logger.severe("An error occurred while loading gamemodes/ShieldPVP/config.yml");
            throw new RuntimeException(e);
        }
    }

}
