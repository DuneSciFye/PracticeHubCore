package me.dunescifye.practicehubcore.utils;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Instant;

public class ClicksPerSecond implements Listener {

    public static Multimap<Player, Instant> leftClicksPerSecond = ArrayListMultimap.create();
    public static Multimap<Player, Instant> rightClicksPerSecond = ArrayListMultimap.create();
    private static PracticeHubCore plugin;

    public void setup(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
        ClicksPerSecond.plugin = plugin;
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        Instant instant = Instant.now();
        Player p = e.getPlayer();
        if (e.getAction().isLeftClick()) {
            leftClicksPerSecond.put(p, instant);
            new BukkitRunnable() {
                @Override
                public void run() {
                    leftClicksPerSecond.remove(p, instant);
                }
            }.runTaskLater(plugin, 20L);
        }
        else {
            rightClicksPerSecond.put(e.getPlayer(), Instant.now());
            new BukkitRunnable() {
                @Override
                public void run() {
                    rightClicksPerSecond.remove(p, instant);
                }
            }.runTaskLater(plugin, 20L);
        }
    }
}
