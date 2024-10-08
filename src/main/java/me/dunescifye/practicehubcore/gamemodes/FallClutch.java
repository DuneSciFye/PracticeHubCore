package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FallClutch implements Listener {
    private static boolean enabled = true;
    private static String[] commandAliases;

    public static World world;
    public static List<Location> grid = new ArrayList<>();
    public static int gridSpacing = 500;
    private static final HashMap<Player, BukkitTask> tasks = new HashMap<>();

    public static void startGame(Player p, ItemStack... items) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        Location spawnLocation = new Location(world, 0, ThreadLocalRandom.current().nextInt(-30, 250), 0);
        while (grid.contains(spawnLocation)) {
            spawnLocation.add(gridSpacing, 0, 0);
        }
        player.setLocation(spawnLocation);

        //Give item
        ItemStack item = items[ThreadLocalRandom.current().nextInt(items.length)];
        player.saveInventory(item);
        player.setItems(items);
        p.teleport(spawnLocation);

        //Check for win
        checkForWin(p, player);

        player.setGamemode("FallClutch");
        PracticeHubPlayer.linkedPlayers.put(p.getUniqueId(), player);
    }

    private static void checkForWin(Player p, PracticeHubPlayer player) {
        BukkitTask task = new BukkitRunnable() {
            int confirm = 0;
            @Override
            public void run() {
                if (!Objects.equals(player.getGamemode(), "FallClutch")) {
                    cancel();
                    return;
                }
                if (p.getY() < -55) {
                    confirm++;
                }
                if (confirm > 20) {
                    cancel();
                    p.sendMessage(Component.text("You win! Starting again in 3 seconds..."));
                    player.increaseSuccesses();
                    player.increaseTotal();
                    Bukkit.getScheduler().runTaskLater(PracticeHubCore.getPlugin(), () -> {
                        p.getInventory().clear();
                        ItemStack[] items = player.getItems();
                        p.getInventory().addItem(items[ThreadLocalRandom.current().nextInt(items.length)]);
                        Location location = player.getLocation().clone();
                        cleanupArea(location);
                        location.setY(ThreadLocalRandom.current().nextDouble(-30, 250));
                        p.teleport(location);
                        confirm = 0;
                    }, 60L);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 2L);
        tasks.put(p, task);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p.getUniqueId());
        if (player == null || !Objects.equals(player.getGamemode(), "FallClutch")) return;

        player.retrieveInventory();
        p.teleport(Config.spawn);
        grid.remove(player.getLocation());
        cleanupArea(player.getLocation());
        tasks.remove(p).cancel();
    }

    private static void cleanupArea(Location location) {
        World world = location.getWorld();
        for (int x = location.getBlockX() - 20; x <= location.getBlockX() + 20; x++) {
            for (int z = location.getBlockZ() - 20; z <= location.getBlockZ() + 20; z++) {
                world.setType(x, -60, z, Material.AIR);
            }
        }
        world.getNearbyEntities(location, 20, 5, 20).forEach(Entity::remove);
    }

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerTakeFallDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());
        if (player == null || !Objects.equals(player.getGamemode(), "FallClutch")) return;

        e.setCancelled(true);
        tasks.remove(p).cancel();
        p.sendMessage(Component.text("You failed! Starting again in 1 second..."));
        player.increaseTotal();
        Bukkit.getScheduler().runTaskLater(PracticeHubCore.getPlugin(), () -> {
            Location location = player.getLocation().clone();
            cleanupArea(location);
            location.setY(ThreadLocalRandom.current().nextDouble(-30, 250));
            p.getInventory().clear();
            ItemStack[] items = player.getItems();
            p.getInventory().addItem(items[ThreadLocalRandom.current().nextInt(items.length)]);
            p.teleport(location);
            player.increaseSuccesses();
            player.increaseTotal();
            checkForWin(p, player);
        }, 20L);
    }
    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        FallClutch.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        FallClutch.commandAliases = commandAliases;
    }


}
