package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;

public class BridgeCommand {

    public static HashMap<Player, String> gamemode = new HashMap<>();
    public static HashMap<Player, ItemStack[]> inventories = new HashMap<>();
    public static HashMap<Player, BukkitTask> tasks = new HashMap<>();

    public static void register(PracticeHubCore plugin) {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((player, args) -> {
                    //Setting up inventory
                    player.sendMessage(Component.text("Starting!"));
                    inventories.put(player, player.getInventory().getContents());
                    Inventory inv = player.getInventory();
                    inv.clear();
                    inv.setItem(0, new ItemStack(Material.OAK_LOG, 64));
                    player.getInventory().setHeldItemSlot(0);
                    gamemode.put(player, "bridge");

                    //Teleport player
                    World world = Bukkit.getWorld("bridge");
                    if (world == null) {
                        player.sendMessage(Component.text("Invalid world. Please contact an administrator."));
                        return;
                    }

                    Location loc = new Location(world, 0, 100, 0);
                    player.teleport(loc);

                    //Loop to check player falling
                    BukkitTask task = new BukkitRunnable() {
                        @Override
                        public void run() {
                            if (player.getVelocity().getY() < -1) {
                                this.cancel();
                                player.getInventory().clear();
                                player.getInventory().setContents(inventories.remove(player));
                                gamemode.remove(player);
                                tasks.remove(player).cancel();
                                player.sendMessage(Component.text("You fell!"));
                                player.teleport(loc);
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
                })
            )
            .withPermission("practicehub.command.bridge")
            .register("practicehub");
    }

}
