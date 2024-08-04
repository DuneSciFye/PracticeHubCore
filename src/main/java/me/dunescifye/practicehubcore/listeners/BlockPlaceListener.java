package me.dunescifye.practicehubcore.listeners;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockPlaceEvent;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class BlockPlaceListener {
    public static HashMap<Player, LinkedList<TimedBlock>> placedBlocks = new HashMap<>();

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
                List<TimedBlock> blocks = placedBlocks.get(p);
                if (blocks == null) {
                    placedBlocks.put(p, new LinkedList<>(List.of(new TimedBlock(b, Instant.now()))));
                    return;
                }
                placedBlocks.get(p).add(new TimedBlock(b, Instant.now()));
            }
            case "PortalBuild" -> {
                List<TimedBlock> blocks = placedBlocks.get(p);
                if (blocks == null) {
                    placedBlocks.put(p, new LinkedList<>(List.of(new TimedBlock(b, Instant.now()))));
                    return;
                }
                placedBlocks.get(p).add(new TimedBlock(b, Instant.now()));
            }
        }
    }
}
