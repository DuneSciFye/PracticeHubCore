package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

public class FallClutch implements Listener {

    public static World world;
    public static List<Location> grid;
    public static int gridSpacing = 500;

    public static void startGame(Player p, ItemStack item) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        Location spawnLocation = new Location(world, 0, ThreadLocalRandom.current().nextInt(-30, 250), 0);
        while (grid.contains(spawnLocation)) {
            spawnLocation.add(gridSpacing, 0, 0);
        }
        player.setLocation(spawnLocation);

        //Give item
        player.saveInventory(item);
        player.setItem(item);
        p.teleport(spawnLocation);

        //Check for win
        new BukkitRunnable() {
            int confirm = 0;
            @Override
            public void run() {
                if (p.getY() < -55) {
                    confirm++;
                }
                if (confirm > 20) {
                    cancel();
                    p.sendMessage(Component.text("You win!"));
                    Location location = spawnLocation.clone();
                    location.setY(ThreadLocalRandom.current().nextDouble(-30, 250));
                    p.teleport(location);
                    player.increaseSuccesses();
                    player.increaseSuccesses();
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 2L);

        player.setGamemode("FallClutch");
        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !Objects.equals(player.getGamemode(), "FallClutch")) return;

        player.retrieveInventory();
        p.teleport(Config.spawn);
        grid.remove(player.getLocation());
    }

    @EventHandler
    public void onPlayerTakeFallDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        if (e.getCause() != EntityDamageEvent.DamageCause.FALL) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !Objects.equals(player.getGamemode(), "FallClutch")) return;

        e.setCancelled(true);
        p.sendMessage("Failed!");
        player.getLocation().setY(ThreadLocalRandom.current().nextDouble(-30, 250));
        p.getInventory().clear();
        p.getInventory().addItem(player.getItem());
        p.teleport(player.getLocation());
        player.increaseTotal();
    }

}
