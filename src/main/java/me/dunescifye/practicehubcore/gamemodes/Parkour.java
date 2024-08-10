package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;

public class Parkour {

    public static String worldName = null;
    public static int[] spawnLocation;
    private static HashMap<Player, BukkitTask> tasks = new HashMap<>();

    public static void startGame(Player p, String gamemode) {
        //Gamemode disabled
        if (worldName == null) return;

        PracticeHubPlayer player = new PracticeHubPlayer(p);
        //Create world
        String copyWorldName = worldName + p.getName();
        PracticeHubCore.worldManager.cloneWorld(worldName, copyWorldName);
        player.setWorldName(copyWorldName);

        World world = Bukkit.getWorld(copyWorldName);
        if (world == null) {
            p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
            return;
        }
        Location loc = new Location(world, spawnLocation[0], spawnLocation[1], spawnLocation[2]);

        //Setup player
        player.saveInventory();
        p.teleport(loc);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);

        //Check for falling
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                if (gamemode == null) {
                    tasks.remove(p).cancel();
                    return;
                }
                if (p.getVelocity().getY() < -1) {
                    p.sendMessage(Component.text("You fell!"));
                    p.teleport(loc);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);
        tasks.put(p, task);

        switch (gamemode) {
            case "1 Block Neo" -> {

            }
            case "2 Block Neo" -> {

            }
            case "3 Block Neo" -> {

            }
            case "easy" -> {

            }
            case "medium" -> {

            }
            case "hard" -> {

            }
        }

        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void endGame(Player p) {
        if (worldName == null) return;

        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
        BukkitTask task = tasks.remove(p);
        if (task != null) task.cancel();
        player.retrieveInventory();
        p.teleport(Config.spawn);
    }

}
