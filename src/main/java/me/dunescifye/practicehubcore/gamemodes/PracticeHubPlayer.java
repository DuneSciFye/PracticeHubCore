package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.utils.TimedBlock;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedList;

public class PracticeHubPlayer {

    public static HashMap<Player, PracticeHubPlayer> linkedPlayers = new HashMap<>();

    private Player player;
    private Instant startTime;
    private Instant finishTime;
    private String lavaSchem;
    private String gamemode;
    private LinkedList<TimedBlock> placedBlocks = new LinkedList<>();
    private ItemStack[] savedInventory;
    private Location location;
    private int launchedArrows = 0;
    private int hitArrows = 0;
    private World world;
    private String worldName;

    public PracticeHubPlayer(Player player) {
        this.player = player;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public int getLaunchedArrows() {
        return launchedArrows;
    }

    public int getHitArrows() {
        return hitArrows;
    }

    public void launchArrow() {
        launchedArrows++;
    }

    public void hitArrow() {
        hitArrows++;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
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

    public void saveInventory(ItemStack... items) {
        Inventory inventory = player.getInventory();
        savedInventory = inventory.getContents();
        inventory.clear();
        for (ItemStack item : items) {
            inventory.addItem(item);
        }

    }



}
