package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import me.dunescifye.practicehubcore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;

public class Bridge implements Listener {
    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();
    public static HashMap<Player, Integer> cps = new HashMap<>();

    public static void startBridgeGame(Player p) {
        p.sendMessage(Component.text("Starting!"));

        //Setting up world
        PracticeHubCore.worldManager.cloneWorld(BridgeConfig.bridgeCopyWorld, "bridge" + p.getName());

        //Teleport player
        World world = Bukkit.getWorld("bridge" + p.getName());
        if (world == null) {
            p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
            return;
        }
        Location loc = new Location(world, 0, 100, 0);
        p.teleport(loc);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);

        //Setting up inventory
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        player.saveInventory(new ItemStack(Material.OAK_LOG, 64));

        player.setGamemode("Bridge");
        PracticeHubPlayer.linkedPlayers.put(p, player);

        //Loop to check p falling
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                //Player fell
                if (p.getVelocity().getY() < -1) {
                    p.sendMessage(Component.text("You fell!"));
                    //Blocks
                    int blockCounter = 0;
                    LinkedList<TimedBlock> blocks = PracticeHubPlayer.linkedPlayers.get(p).getPlacedBlocks();

                    //Distance
                    if (blocks != null && !blocks.isEmpty()) {
                        for (TimedBlock b : blocks) {
                            b.getBlock().setType(Material.AIR);
                            blockCounter++;
                        }
                        p.sendMessage(Component.text("You placed " + blockCounter + " blocks!"));

                        if (blocks.size() > 2) {
                            //Time
                            Duration duration = Duration.between(blocks.getFirst().getTime(), blocks.getLast().getTime());

                            Location newLoc = blocks.getLast().getBlock().getLocation();
                            double distance = newLoc.distance(loc);
                            int x = Math.abs(newLoc.getBlockX());
                            int y = Math.abs(newLoc.getBlockY() - 100);
                            int z = Math.abs(newLoc.getBlockZ());
                            p.sendMessage(Component.text("You went " + String.format("%.2f", distance) + " blocks from 0 0" + "(" + x + ", " + y + ", " + z + ")"));

                            //Speed
                            double time = (double) duration.toMillis() / 1000;
                            p.sendMessage(Component.text("You travelled at a speed of " + String.format("%.2f", distance / time) + "m/s (" + String.format("%.2f", x / time) + "m/s, " + String.format("%.2f", y / time) + "m/s, " + String.format("%.2f", z / time) + "m/s)"));

                            //Times
                            p.sendMessage(Component.text(Utils.getFormattedTime(duration)));

                            //CPS
                            if (time > 1) {
                                p.sendMessage(Component.text("You clicked " + String.format("%.2f", cps.get(p) / time) + " times per second."));
                                cps.remove(p);
                            } else {
                                p.sendMessage(Component.text("You didn't last long enough to get a CPS"));
                            }
                        }

                    }

                    p.teleport(loc);
                    p.setFoodLevel(20);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 5L);

        tasks.put(p, task);
    }

    public static void endBridgeGame(Player p) {
        p.getInventory().clear();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        p.getInventory().setContents(player.getSavedInventory());
        Bridge.tasks.remove(p).cancel();
        p.sendMessage(Component.text("Ended game!"));
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (e.getAction().isLeftClick()) return;
        Player p = e.getPlayer();
        cps.put(p, cps.getOrDefault(p, 0) + 1);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player.getGamemode().equals("Bridge")) {
            PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
            p.teleport(Config.spawn);
        }
    }
}
