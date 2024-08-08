package me.dunescifye.practicehubcore.listeners;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null) return;
        String gamemode = player.getGamemode();
        if (gamemode.equals("Bridge") || gamemode.equals("FallClutch")) e.setCancelled(true);
    }

}
