package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LilyPadBridge {
    private static boolean enabled = true;
    private static String[] commandAliases;

    public static String copyWorld = null;

    public static void startGame(Player p) {
        if (copyWorld == null) return;
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        player.setGamemode("LilyPadBridge");

        PracticeHubCore.worldManager.cloneWorld(copyWorld, "lilyPadBridge" + p.getName());
        player.setWorldName("lilyPadBridge" + p.getName());
        World world = Bukkit.getWorld("lilyPadBridge" + p.getName());
        Location teleportLocation = new Location(world, 0, -60, 0);
        p.teleport(teleportLocation);
        player.setLocation(teleportLocation);

        player.saveInventory(new ItemStack(Material.LILY_PAD, 64));
        PracticeHubPlayer.linkedPlayers.put(p.getUniqueId(), player);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p.getUniqueId());
        player.retrieveInventory();
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld(player.getWorldName());
    }

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        LilyPadBridge.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        LilyPadBridge.commandAliases = commandAliases;
    }
}
