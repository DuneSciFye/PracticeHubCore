package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.BowAim;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BowAimConfig {
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/BowAim/BowAim.yml"), plugin.getResource("gamemodes/BowAim/BowAim.yml"));
            //Copy World
            String worldName = config.getString("BowWorld.World");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Bow Aim gamemode disabled until fixed.");
                return;
            } else {
                BowAim.world = world;
            }

            //Lava Pools
            Section schematics = config.getSection("Schematics");
            if (schematics != null) {
                for (Object key : schematics.getKeys()) {
                    if (key instanceof String keyString) {
                        Section keySection = schematics.getSection(keyString);
                        String file = keySection.getString("file");
                        List<Location> playerSpawnLocations = new ArrayList<>();
                        for (String location : keySection.getStringList("playerSpawnLocations")) {
                            String[] coords = location.split(" ", 5);
                            if (coords.length < 3) {
                                logger.warning("Schematic location " + file + " for Bow Aim gamemode is missing coordinates! Current: " + location);
                                continue;
                            }
                            Location loc = new Location(world, Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
                            if (coords.length < 5) {
                                playerSpawnLocations.add(loc);
                                continue;
                            }
                            loc.setYaw(Float.parseFloat(coords[3]));
                            loc.setPitch(Float.parseFloat(coords[3]));
                            playerSpawnLocations.add(loc);
                        }
                        List<int[]> blockSpawnLocations = new ArrayList<>();
                        for (String location : keySection.getStringList("blockSpawnLocations")) {
                            int[] coords = location.split(" ");
                            if (coords.length != 6) {
                                logger.warning("Block Spawn location " + file + " for Bow Aim gamemode is missing coordinates! Current: " + location);
                                continue;
                            }

                            blockSpawnLocations.add(coords);
                        }
                        BowAim bowAim = new BowAim(playerSpawnLocations, blockSpawnLocations);
                    }
                }
            }

            //Grid Spacing
            BowAim.gridSpacing = config.getInt("BowWorld.Spacing");

        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/BowWorld/BowWorld.yml");
            throw new RuntimeException(e);
        }

        //Create schematic director
        File schematics = new File(plugin.getDataFolder(), "gamemodes/BowWorld/Schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }

    }


}

