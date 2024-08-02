package me.dunescifye.practicehubcore.utils;

import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;

public class ClicksPerSecond implements Listener {

    public static HashMap<Player, Integer> clicksPerSecond = new HashMap<>();

    public void setup(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);

        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {

                }
            }
        }.runTaskTimer(plugin, 20L, 20L);
    }

    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent e) {
        if (e.getAction().isLeftClick()) return;
        Player p = e.getPlayer();
        clicksPerSecond.put(p, clicksPerSecond.getOrDefault(p, 0) + 1);
    }
}
