package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ShieldPVPConfig {

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "gamemodes/ShieldPVP/config.yml"), plugin.getResource("gamemodes/ShieldPVP/config.yml"));
            boolean enabled = config.getBoolean("Enabled");
            ShieldPVP.setEnabled(enabled);

            if (!enabled) return;
            String worldName = config.getString("World.WorldName");
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                logger.severe("Shield PVP World is invalid! Gamemode will be disabled until fixed.");
                ShieldPVP.setEnabled(false);
                return;
            }
            ShieldPVP.setWorldName(worldName);
            ShieldPVP.setWorld(world);
            ShieldPVP.setCommandAliases(config.getStringList("CommandAliases").toArray(new String[0]));


            Section schematics = config.getSection("Schematics");
            if (schematics != null) {
                for (Object key : schematics.getKeys()) {
                    if (key instanceof String keyString) {
                        Section keySection = schematics.getSection(keyString);
                        String file = keySection.getString("File");
                        List<Location> locations = new ArrayList<>();
                        for (String location : keySection.getStringList("SpawnLocations")) {
                            String[] coords = location.split(",", 5);
                            if (coords.length < 3) {
                                logger.warning("Schematic location " + file + " for Shield PVP gamemode is malformed! Current: " + location);
                                continue;
                            }
                            Location loc = new Location(world, Double.parseDouble(coords[0]), Double.parseDouble(coords[1]), Double.parseDouble(coords[2]));
                            if (coords.length < 5) {
                                locations.add(loc);
                                continue;
                            }
                            loc.setYaw(Float.parseFloat(coords[3]));
                            loc.setPitch(Float.parseFloat(coords[3]));
                            locations.add(loc);
                        }
                        if (locations.size() < 2) {
                            logger.warning("Schematic " + file + " has less than 2 locations!");
                        } else {
                            ShieldPVP.addSchematic(file, locations);
                        }
                    }
                }
            }

            //Grid Spacing
            ShieldPVP.setSpacing(config.getInt("World.Spacing"));
        } catch (
            IOException e) {
            logger.severe("An error occurred while loading gamemodes/ShieldPVP/config.yml");
            throw new RuntimeException(e);
        }

        //Create schematic director
        File schematics = new File(plugin.getDataFolder(), "gamemodes/PortalBuild/Schematics");
        if (!schematics.exists()) {
            schematics.mkdir();
        }
    }

}
