package me.dunescifye.practicehubcore.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.time.Duration;

public class Utils {

    public static String getFormattedTime(Duration duration) {
        if (duration.compareTo(Duration.ofHours(1)) > 0) {
            return duration.toHoursPart() + " hours, "
                + duration.toMinutesPart() + " minutes, & "
                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        } else if (duration.compareTo(Duration.ofMinutes(1)) > 0) {
            return duration.toMinutesPart() + " minutes & "
                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        } else {
            return duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        }
    }
    public static void cleanupArea(Location location) {
        Block origin = location.getBlock();
        for (int x = -100; x < 100; x++) {
            for (int y = -64; y < 320; y++) {
                for (int z = -100; z < 100; z++) {
                    origin.getRelative(x, y, z).setType(Material.AIR);
                }
            }
        }
    }

}
