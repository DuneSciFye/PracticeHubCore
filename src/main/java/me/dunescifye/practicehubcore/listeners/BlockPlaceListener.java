package me.dunescifye.practicehubcore.listeners;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.time.Instant;

public class BlockPlaceListener implements Listener {

    public void playerBlockPlaceHandler(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        String currentGamemode = PracticeHubCore.gamemode.get(p);
        if (currentGamemode == null) return;
        Block b = e.getBlockPlaced();
        switch (currentGamemode) {
            case "bridge" -> {
                e.getItemInHand().setAmount(64);
                PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                player.addPlacedBlock(new TimedBlock(b, Instant.now()));
            }
            case "PortalBuild" -> {
                PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                player.addPlacedBlock(new TimedBlock(b, Instant.now()));
            }
        }
    }
}
