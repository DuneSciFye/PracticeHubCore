package me.dunescifye.practicehubcore.gamemodes;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Bridge implements Listener {
    public static HashMap<Player, String> gamemode = new HashMap<>();
    public static HashMap<Player, ItemStack[]> inventories = new HashMap<>();
    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();
    public static HashMap<Player, List<Block>> placedBlocks = new HashMap<>();

    public static void register(PracticeHubCore plugin) {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((player, args) -> {
                    player.sendMessage(Component.text("Starting!"));

                    //Setting up world
                    PracticeHubCore.worldManager.cloneWorld("baseBridge", "bridge" + player.getName());

                    //Teleport player
                    World world = Bukkit.getWorld("bridge" + player.getName());
                    if (world == null) {
                        player.sendMessage(Component.text("Invalid world. Please contact an administrator."));
                        return;
                    }
                    Location loc = new Location(world, 0, 100, 0);
                    player.teleport(loc);
                    player.setGameMode(GameMode.SURVIVAL);
                    player.setFoodLevel(20);

                    //Setting up inventory
                    inventories.put(player, player.getInventory().getContents());
                    Inventory inv = player.getInventory();
                    inv.clear();
                    inv.setItem(0, new ItemStack(Material.OAK_LOG, 64));
                    player.getInventory().setHeldItemSlot(0);
                    gamemode.put(player, "bridge");


                    placedBlocks.put(player, new ArrayList<>());

                    //Loop to check player falling
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            //Player fell
                            if (player.getVelocity().getY() < -1) {
                                player.sendMessage(Component.text("You fell!"));
                                //Blocks
                                int blocksPlaced = 0;
                                for (Block b : placedBlocks.get(player)) {
                                    b.setType(Material.AIR);
                                    blocksPlaced++;
                                }
                                player.sendMessage(Component.text("You placed " + blocksPlaced + " blocks!"));
                                placedBlocks.put(player, new ArrayList<>());

                                //Distance
                                Location newLoc = player.getLocation();
                                player.sendMessage(Component.text("You went " + newLoc.getX() + " blocks in the X"));
                                player.sendMessage(Component.text("You went " + (newLoc.getY() - 100) + " blocks in the Y"));
                                player.sendMessage(Component.text("You went " + newLoc.getZ() + " blocks in the Z"));
                                player.sendMessage(Component.text("You went " + newLoc.distance(loc) + " blocks from 0 0"));

                                player.teleport(loc);
                                player.setFoodLevel(20);
                            }
                        }
                    }.runTaskTimer(plugin, 0L, 5L);

                    tasks.put(player, task);
                })
            )
            .then(new LiteralArgument("end")
                .executesPlayer((player, args) -> {
                    String currentGamemode = gamemode.get(player);
                    if (currentGamemode == null) {
                        player.sendMessage(Component.text("You are not in any game!"));
                        return;
                    }
                    player.getInventory().clear();
                    player.getInventory().setContents(inventories.remove(player));
                    gamemode.remove(player);
                    tasks.remove(player).cancel();
                    player.sendMessage(Component.text("Ended game!"));
                    PracticeHubCore.worldManager.deleteWorld("bridge" + player.getName());
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
            e.getItemInHand().setAmount(64);
            placedBlocks.get(p).add(e.getBlockPlaced());
        }
    }
}
