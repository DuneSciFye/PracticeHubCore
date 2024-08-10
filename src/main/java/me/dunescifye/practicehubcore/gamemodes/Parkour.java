package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.Vector;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;

public class Parkour {

    public static String worldName = null;
    public static int[] spawnLocation;
    private static HashMap<Player, BukkitTask> tasks = new HashMap<>();
    private static HashMap<Player, BukkitTask> successTasks = new HashMap<>();

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

                if (p.getVelocity().getY() < -1) {
                    LinkedList<TimedBlock> blocks = PracticeHubPlayer.linkedPlayers.get(p).getPlacedBlocks();
                    if (blocks != null && !blocks.isEmpty()) {
                        for (TimedBlock b : blocks) {
                            b.getBlock().setType(Material.AIR);
                        }
                    }
                    successTasks.remove(p).cancel();
                    checkFall(p, player, world, gamemode);
                    p.sendMessage(Component.text("You fell!"));
                    p.setVelocity(new Vector().zero());
                    p.teleport(loc);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);
        checkFall(p, player, world, gamemode);
        tasks.put(p, task);
        player.setGamemode("Parkour");
        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    private static void checkFall(Player p, PracticeHubPlayer player, World world, String gamemode) {

        switch (gamemode) {
            case "1 Block Neo" -> {
                BukkitTask checkForJump = new BukkitRunnable() {
                    int x = -1;
                    @Override
                    public void run() {
                        if (p.getX() > x && p.getY() > 99) {
                            world.setType(x + 2, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 99, 0), Instant.now()));
                            world.setType(x + 2, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 100, 0), Instant.now()));
                            world.setType(x + 2, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 101, 0), Instant.now()));
                            world.setType(x + 3, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 99, 0), Instant.now()));
                            world.setType(x + 4, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 4, 99, 0), Instant.now()));
                            x += 3;
                        }
                    }
                }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);
                successTasks.put(p, checkForJump);
            }
            case "2 Block Neo" -> {
                BukkitTask checkForJump = new BukkitRunnable() {
                    int x = -1;
                    @Override
                    public void run() {
                        if (p.getX() > x && p.getY() > 99) {
                            world.setType(x + 2, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 99, 0), Instant.now()));
                            world.setType(x + 2, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 100, 0), Instant.now()));
                            world.setType(x + 2, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 101, 0), Instant.now()));
                            world.setType(x + 3, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 99, 0), Instant.now()));
                            world.setType(x + 3, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 100, 0), Instant.now()));
                            world.setType(x + 3, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 101, 0), Instant.now()));
                            world.setType(x + 4, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 4, 99, 0), Instant.now()));
                            world.setType(x + 5, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 5, 99, 0), Instant.now()));
                            x += 4;
                        }
                    }
                }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);
                successTasks.put(p, checkForJump);
            }
            case "3 Block Neo" -> {
                BukkitTask checkForJump = new BukkitRunnable() {
                    int x = -1;
                    @Override
                    public void run() {
                        if (p.getX() > x && p.getY() > 99) {
                            world.setType(x + 2, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 99, 0), Instant.now()));
                            world.setType(x + 2, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 100, 0), Instant.now()));
                            world.setType(x + 2, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 2, 101, 0), Instant.now()));
                            world.setType(x + 3, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 99, 0), Instant.now()));
                            world.setType(x + 3, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 100, 0), Instant.now()));
                            world.setType(x + 3, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 3, 101, 0), Instant.now()));
                            world.setType(x + 4, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 4, 99, 0), Instant.now()));
                            world.setType(x + 4, 100, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 4, 100, 0), Instant.now()));
                            world.setType(x + 4, 101, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 4, 101, 0), Instant.now()));
                            world.setType(x + 5, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 5, 99, 0), Instant.now()));
                            world.setType(x + 6, 99, 0, Material.STONE);
                            player.addPlacedBlock(new TimedBlock(world.getBlockAt(x + 6, 99, 0), Instant.now()));
                            x += 5;
                        }
                    }
                }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);
                successTasks.put(p, checkForJump);
            }
            case "easy" -> {

            }
            case "medium" -> {

            }
            case "hard" -> {

            }
        }
    }

    public static void endGame(Player p) {
        if (worldName == null) return;

        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
        BukkitTask task = tasks.remove(p);
        if (task != null) task.cancel();
        BukkitTask successTask = successTasks.remove(p);
        if (successTask != null) successTask.cancel();
        player.retrieveInventory();
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld(player.getWorldName());
    }

}
