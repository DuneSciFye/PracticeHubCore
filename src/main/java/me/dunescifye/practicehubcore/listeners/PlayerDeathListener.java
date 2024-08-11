package me.dunescifye.practicehubcore.listeners;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.Objects;

public class PlayerDeathListener implements Listener {

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player p = e.getPlayer();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());
        if (!Objects.equals(player.getGamemode(), "Shield PVP")) return;

        e.setCancelled(true);

        ShieldPVP.restartGame(p);
    }

}
