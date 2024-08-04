package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

public class PortalBuildConfig {

    public static World portalBuildWorld = null;
    public static HashMap<String, List<Location>> lavaPools = new HashMap<>();
    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/PortalBuild/PortalBuild.yml"), plugin.getResource("gamemodes/PortalBuild/PortalBuild.yml"));
            //Copy World
            String worldName = config.getString("BuildWorld");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("World \"" + worldName + "\" not found! Portal Build gamemode disabled until fixed.");
                return;
            } else {
                portalBuildWorld = world;
            }

            //Lava Pools
            Section schematics = config.getSection("Schematics");
            if (schematics != null) {
                for (Object key : schematics.getKeys()) {
                    if (key instanceof String keyString) {
                        Section keySection = schematics.getSection(keyString);
                        String file = keySection.getString("file");
                        List<Location> locations = new ArrayList<>();
                        for (String location : keySection.getStringList("locations")) {
                            String[] coords = location.split(",", 5);
                            if (coords.length < 3) {
                                logger.warning("Schematic location " + file + " for Portal Build gamemode is malformed! Current: " + location);
                                continue;
                            }
                            Location loc = new Location(portalBuildWorld, Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
                            if (coords.length < 5) {
                                locations.add(loc);
                                continue;
                            }
                            loc.setYaw(Float.parseFloat(coords[3]));
                            loc.setPitch(Float.parseFloat(coords[3]));
                            locations.add(loc);
                        }
                        lavaPools.put(file, locations);
                    }
                }
            }

        } catch (
            IOException e) {
            logger.severe("Failed to load file gamemodes/PortalBuild.yml");
            throw new RuntimeException(e);
        }

        //Create schematic director
        File schematics = new File(plugin.getDataFolder(), "gamemodes/PortalBuild/Schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }

    }


}
