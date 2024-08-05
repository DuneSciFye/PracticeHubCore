package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.utils.TimedBlock;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;

public class PracticeHubPlayer {

    public static HashMap<Player, PracticeHubPlayer> linkedPlayers = new HashMap<>();

    private Instant startTime;
    private Instant finishTime;
    private String lavaSchem;
    private String gamemode;
    private LinkedList<TimedBlock> placedBlocks = new LinkedList<>();
    private ItemStack[] savedInventory;

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

    public LinkedList<TimedBlock> getPlacedBlocks() {
        return placedBlocks;
    }

    public void addPlacedBlock(TimedBlock b) {
        placedBlocks.add(b);
    }

    public String getGamemode() {
        return gamemode;
    }

    public void setGamemode(String gamemode) {
        this.gamemode = gamemode;
    }

    public ItemStack[] getSavedInventory() {
        return savedInventory;
    }

    public void setSavedInventory(ItemStack[] savedInventory) {
        this.savedInventory = savedInventory;
    }


}