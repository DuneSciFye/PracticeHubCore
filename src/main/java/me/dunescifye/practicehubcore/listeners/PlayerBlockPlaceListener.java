package me.dunescifye.practicehubcore.listeners;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.commands.BridgeCommand;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBlockPlaceListener implements Listener {

    public void playerBlockPlaceHandler(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();
        String currentGamemode = BridgeCommand.gamemode.get(p);
        if (currentGamemode == null) return;
        if (currentGamemode.equals("bridge")) {
            e.getItemInHand().setAmount(64);
        }
    }

}
