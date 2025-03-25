package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public abstract class Gamemode {

    public static boolean enabled = true;
    public static String[] commandAliases;
    public static String worldName;
    public static World world;
    public ItemStack[] savedInventory;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        Gamemode.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        Gamemode.commandAliases = commandAliases;
    }

    public static World getWorld() {
        return world;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void setWorldName(String worldName) {
        Gamemode.worldName = worldName;
    }

    public static void setWorld(World world) {
        Gamemode.world = world;
    }

    public abstract void start();
    public abstract void end();
}
