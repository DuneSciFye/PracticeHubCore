package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import me.dunescifye.practicehubcore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;

public class Bridge implements Listener {
    private static boolean enabled = true;
    private static String[] commandAliases;
    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();
    public static HashMap<Player, Integer> cps = new HashMap<>();
    private static String startingMessage = "&fStarting!";
    private static String copyWorldName;
    private static String fallingMessage = "&cYou fell!";

    public static void startBridgeGame(Player p, String gamemode) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(startingMessage));

        //Setting up world
        String newWorldName = copyWorldName + p.getName();
        PracticeHubCore.worldManager.cloneWorld(copyWorldName, newWorldName);
        World world = Bukkit.getWorld("bridge" + p.getName());
        if (world == null) {
            p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
            return;
        }
        player.setWorldName(newWorldName);

        //Setting up player
        Location loc = new Location(world, 0, 100, 0);
        p.teleport(loc);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);
        player.saveInventory(new ItemStack(Material.OAK_LOG, 64));
        player.setGamemode("Bridge");
        PracticeHubPlayer.linkedPlayers.put(p.getUniqueId(), player);

        //Loop to check p falling
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                //Player fell
                if (p.getVelocity().getY() < -1) {
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(fallingMessage));
                    //Blocks
                    int blockCounter = 0;
                    LinkedList<TimedBlock> blocks = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId()).getPlacedBlocks();

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
                            p.sendMessage(Component.text("You lasted " + Utils.getFormattedTime(duration)));

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


        if (gamemode.equals("100m")) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (Math.abs(p.getX()) >= 100 || Math.abs(p.getZ()) >= 100) {
                        cancel();
                        p.sendMessage("You win!");
                        //Times
                        LinkedList<TimedBlock> blocks = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId()).getPlacedBlocks();
                        Duration duration = Duration.between(blocks.getFirst().getTime(), blocks.getLast().getTime());
                        p.sendMessage(Component.text("Time took: " + Utils.getFormattedTime(duration)));
                        endBridgeGame(p);
                    }
                }
            }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 1L);
        }

        tasks.put(p, task);
    }

    public static void endBridgeGame(Player p) {
        p.getInventory().clear();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p.getUniqueId());
        p.getInventory().setContents(player.getSavedInventory());
        Bridge.tasks.remove(p).cancel();
        p.sendMessage(Component.text("Ended game!"));
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
    }

    public static void setStartingMessage(String startingMessage) {
        Bridge.startingMessage = startingMessage;
    }

    public static void setCopyWorldName(String copyWorldName) {
        Bridge.copyWorldName = copyWorldName;
    }
    public static String getCopyWorldName() {
        return copyWorldName;
    }

    public static void setFallingMessage(String fallingMessage) {
        Bridge.fallingMessage = fallingMessage;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (e.getAction().isLeftClick()) return;
        Player p = e.getPlayer();
        cps.put(p, cps.getOrDefault(p, 0) + 1);
    }
    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        Bridge.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        Bridge.commandAliases = commandAliases;
    }

}
