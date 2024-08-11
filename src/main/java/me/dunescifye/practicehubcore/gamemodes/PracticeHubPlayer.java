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
import java.util.UUID;

public class PracticeHubPlayer{

    public static HashMap<UUID, PracticeHubPlayer> linkedPlayers = new HashMap<>();

    private final Player player;
    private Instant startTime;
    private String lavaSchem;
    private String gamemode;
    private final LinkedList<TimedBlock> placedBlocks = new LinkedList<>();
    private ItemStack[] savedInventory;
    private Location location;
    private int total = 0;
    private int successes = 0;
    private World world;
    private String worldName;
    private BowAim bowAim;
    private ItemStack[] items;

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

    public int getTotal() {
        return total;
    }

    public int getSuccesses() {
        return successes;
    }

    public void increaseTotal() {
        total++;
    }

    public void increaseSuccesses() {
        successes++;
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

    public void saveInventory(ItemStack... items) {
        Inventory inventory = player.getInventory();
        savedInventory = inventory.getContents();
        inventory.clear();
        for (ItemStack item : items) {
            inventory.addItem(item);
        }

    }

    public void retrieveInventory() {
        player.getInventory().clear();
        player.getInventory().setContents(savedInventory);
    }

    public BowAim getBowAim() {
        return bowAim;
    }

    public void setBowAim(BowAim bowAim) {
        this.bowAim = bowAim;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public Player getPlayer() {
        return player;
    }

    public static void addPlayer(Player p) {
        linkedPlayers.put(p.getUniqueId(), new PracticeHubPlayer(p));
    }

}
