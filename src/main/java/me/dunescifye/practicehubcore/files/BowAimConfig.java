package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.BowAim;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
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
                        System.out.println("eeeee");
                        blockLocation: for (String location : keySection.getStringList("blockSpawnLocations")) {
                            System.out.println("AAAAAAAAAAAAAAAAAA");
                            String[] coords = location.split(" ");
                            if (coords.length != 6) {
                                logger.warning("Block Spawn location " + file + " for Bow Aim gamemode has incorrect number of coordinates! Current: " + location);
                                System.out.println("a");
                                continue;
                            }
                            System.out.println("b");
                            for (String coord : coords) {
                                if (!coord.matches("-?\\d+(\\.\\d+)?")) {
                                    logger.warning("Block Spawn location " + file + " for Bow Aim gamemode doesn't have integers! Current: " + location);
                                    continue blockLocation;
                                }
                            }
                            System.out.println("c");
                            for (int x = Integer.parseInt(coords[0]); x <= Integer.parseInt(coords[3]); x++) {
                                for (int y = Integer.parseInt(coords[1]); y <= Integer.parseInt(coords[4]); y++) {
                                    for (int z = Integer.parseInt(coords[2]); z <= Integer.parseInt(coords[5]); z++) {
                                        blockSpawnLocations.add(new int[]{x, y, z});
                                    }
                                }
                            }
                            System.out.println("d");
                        }
                        System.out.println(blockSpawnLocations);
                        //Get Number of Blocks
                        int numberOfBlocks = keySection.getInt("NumberOfBlocks");
                        //Get block materials
                        List<Material> blocks = new ArrayList<>();
                        for (String matName : keySection.getStringList("BlockMaterials")) {
                            Material material = Material.valueOf(matName);
                            if (material != null) blocks.add(material);
                        }
                        BowAim bowAim = new BowAim(file, playerSpawnLocations, blockSpawnLocations, numberOfBlocks, blocks);
                        BowAim.bowAims.add(bowAim);
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
        File schematics = new File(plugin.getDataFolder(), "gamemodes/BowAim/Schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }

    }


}

