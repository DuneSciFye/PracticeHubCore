package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import net.kyori.adventure.text.Component;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class FallClutch {

    public static World world;
    public static List<Location> grid;
    public static int gridSpacing = 500;

    public static void startGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        Location spawnLocation = new Location(world, 0, ThreadLocalRandom.current().nextInt(-30, 250), 0);
        while (grid.contains(spawnLocation)) {
            spawnLocation.add(gridSpacing, 0, 0);
        }

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
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 2L);


        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

}
