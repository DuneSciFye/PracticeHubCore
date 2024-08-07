package me.dunescifye.practicehubcore.gamemodes;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class BowAim implements Listener {

    public static World world;
    private List<Location> playerSpawnLocations = new ArrayList<>();
    private List<int[]> blockSpawnLocations = new ArrayList<>();
    private int numberOfBlocks = 3;
    private List<Material> blocks = new ArrayList<>();
    private String fileName = null;
    public static ArrayList<Location> grid = new ArrayList<>();
    public static int gridSpacing;
    public static ArrayList<BowAim> bowAims = new ArrayList<>();

    public BowAim() {
    }

    public BowAim(String fileName, List<Location> playerSpawnLocations, List<int[]> blockSpawnLocations, int numberOfBlocks, List<Material> blocks) {
        this.fileName = fileName;
        this.playerSpawnLocations = playerSpawnLocations;
        this.blockSpawnLocations = blockSpawnLocations;
        this.numberOfBlocks = numberOfBlocks;
        this.blocks = blocks;
    }

    public static void startBowAimGame(Player p) {
        Plugin plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();
        PracticeHubPlayer player = new PracticeHubPlayer(p);

        //Setting up schematic
        //Get Location
        Location location = new Location(world, 0, 100, 0);
        while (grid.contains(location)) {
            location.add(gridSpacing, 0, 0);
        }
        player.setLocation(location);
        grid.add(location);

        //Paste schematic
        //Get random map
        BowAim bowAim = bowAims.get(ThreadLocalRandom.current().nextInt(bowAims.size()));
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "gamemodes/BowAim/Schematics/" + bowAim.fileName);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        if (format == null) {
            logger.severe("Schematic " + bowAim.fileName + " not found for Bow Aim gamemode.");
            return;
        }

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            p.sendMessage("There was nothing on the clipboard! Report to an administrator!");
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(world))) {
            Operation operation = new ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(location.getX(), 100, 0))
                .copyBiomes(false)
                .copyEntities(false)
                .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            p.sendMessage("An error occurred! Report to an administrator!");
            throw new RuntimeException(e);
        }
        //Setting up inventory
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = bow.getItemMeta();
        meta.setUnbreakable(true);
        bow.setItemMeta(meta);
        player.saveInventory(bow,
            new ItemStack(Material.ARROW));
        //Teleport Player
        p.teleport(bowAim.playerSpawnLocations.get(ThreadLocalRandom.current().nextInt(bowAim.playerSpawnLocations.size())));


        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {

    }

}
