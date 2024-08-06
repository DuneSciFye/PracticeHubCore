package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.bowboost.BowBoost;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class BowBoostConfig {
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/BowBoost/BowBoost.yml"), plugin.getResource("gamemodes/BowBoost/BowBoost.yml"));
            //Copy World
            String worldName = config.getString("CopyWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Bridge gamemode disabled until fixed.");
            } else {
                BowBoost.bowBoostCopyWorld = worldName;
            }
            //100m Copy World
            worldName = config.getString("100mCopyWorld");
            world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! 100m Bridge gamemode disabled until fixed.");
            } else {
                BowBoost.bowBoost100mCopyWorld = worldName;
            }

            //Hit Message
            BowBoost.hitMessage = config.getString("Messages.HitMessage");
            //Start time 100m
            BowBoost.startTime100m = config.getInt("100mStartTime");
        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/BowBoost.yml");
            throw new RuntimeException(e);
        }
    }

}
