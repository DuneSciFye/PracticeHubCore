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
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
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

public class BowAim {

    public static World world;
    private List<Location> playerSpawnLocations;
    private List<int[]> blockSpawnLocations;
    private int numberOfBlocks = 3;
    private List<Material> blocks;
    public static ArrayList<Location> grid = new ArrayList<>();
    public static int gridSpacing;
    public static ArrayList<BowAim> bowAims = new ArrayList<>();
    public BowAim(List<Location> playerSpawnLocations, List<int[]> blockSpawnLocations, int numberOfBlocks, List<Material> blocks) {
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
        List<String> schematics = new ArrayList<>(playerSpawnLocations.keySet());
        String fileName = schematics.get(ThreadLocalRandom.current().nextInt(schematics.size()));
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "gamemodes/BowAim/Schematics/" + fileName);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        if (format == null) {
            logger.severe("Schematic " + fileName + " not found for Bow Aim gamemode.");
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
        List<Location> teleportLoc = playerSpawnLocations.get(fileName);
        p.teleport(teleportLoc.get(ThreadLocalRandom.current().nextInt(teleportLoc.size())));


        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

}
