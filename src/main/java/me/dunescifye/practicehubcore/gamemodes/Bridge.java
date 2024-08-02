package me.dunescifye.practicehubcore.gamemodes;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Bridge implements Listener {
    public static HashMap<Player, String> gamemode = new HashMap<>();
    public static HashMap<Player, ItemStack[]> inventories = new HashMap<>();
    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();
    public static HashMap<Player, LinkedList<TimedBlock>> placedBlocks = new HashMap<>();
    public static HashMap<Player, Integer> cps = new HashMap<>();

    public static void register(PracticeHubCore plugin) {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    p.sendMessage(Component.text("Starting!"));

                    //Setting up world
                    PracticeHubCore.worldManager.cloneWorld("baseBridge", "bridge" + p.getName());

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
                    inventories.put(p, p.getInventory().getContents());
                    Inventory inv = p.getInventory();
                    inv.clear();
                    inv.setItem(0, new ItemStack(Material.OAK_LOG, 64));
                    p.getInventory().setHeldItemSlot(0);
                    
                    gamemode.put(p, "bridge");

                    //Loop to check p falling
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            //Player fell
                            if (p.getVelocity().getY() < -1) {
                                p.sendMessage(Component.text("You fell!"));
                                //Blocks
                                int blockCounter = 0;
                                LinkedList<TimedBlock> blocks = placedBlocks.remove(p);

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
                                        if (duration.compareTo(Duration.ofHours(1)) > 0) {
                                            p.sendMessage(Component.text("You lasted "
                                                + duration.toHoursPart() + " hours, "
                                                + duration.toMinutesPart() + " minutes, & "
                                                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds."));
                                        } else if (duration.compareTo(Duration.ofMinutes(1)) > 0) {
                                            p.sendMessage(Component.text("You lasted "
                                                + duration.toMinutesPart() + " minutes & "
                                                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds."));
                                        } else {
                                            p.sendMessage(Component.text("You lasted "
                                                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds."));
                                        }

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
                    }.runTaskTimer(plugin, 0L, 5L);

                    tasks.put(p, task);
                })
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    String currentGamemode = gamemode.get(p);
                    if (currentGamemode == null) {
                        p.sendMessage(Component.text("You are not in any game!"));
                        return;
                    }
                    p.getInventory().clear();
                    p.getInventory().setContents(inventories.remove(p));
                    gamemode.remove(p);
                    tasks.remove(p).cancel();
                    p.sendMessage(Component.text("Ended game!"));
                    PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
                })
            )
            .withPermission("practicehub.command.bridge")
            .register("practicehub");
    }
    public void playerBlockPlaceHandler(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        String currentGamemode = gamemode.get(p);
        if (currentGamemode == null) return;
        if (currentGamemode.equals("bridge")) {
            Block b = e.getBlockPlaced();
            e.getItemInHand().setAmount(64);
            List<TimedBlock> blocks = placedBlocks.get(p);
            if (blocks == null) {
                placedBlocks.put(p, new LinkedList<>(List.of(new TimedBlock(b, Instant.now()))));
                return;
            }
            placedBlocks.get(p).add(new TimedBlock(b, Instant.now()));
        }
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (e.getAction().isLeftClick()) return;
        Player p = e.getPlayer();
        cps.put(p, cps.getOrDefault(p, 0) + 1);
    }
}
