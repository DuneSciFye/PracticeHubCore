package me.dunescifye.practicehubcore.gamemodes.portalbuild;

import org.bukkit.entity.Player;

import java.time.Instant;

public class PortalBuildPlayer {

    private final Player player;
    private Instant startTime;
    private Instant finishTime;
    private String lavaSchem;

    public PortalBuildPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getStartTime() {
        return startTime;
    }
    public void setFinishTime(Instant finishTime) {
        this.finishTime = finishTime;
    }

    public Instant getFinishTime() {
        return finishTime;
    }
    public void setLavaSchem(String lavaSchem) {
        this.lavaSchem = lavaSchem;
    }
    public String getLavaSchem() {
        return lavaSchem;
    }

}
