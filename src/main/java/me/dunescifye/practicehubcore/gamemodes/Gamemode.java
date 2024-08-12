package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.World;

public class Gamemode {

    private static boolean enabled = true;
    private static String[] commandAliases;
    private static String worldName;
    private static World world;



    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        this.commandAliases = commandAliases;
    }

    public static World getWorld() {
        return world;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public static void setWorld(World world) {
        this.world = world;
    }

}
