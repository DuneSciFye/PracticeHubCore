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
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.utils.Utils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class BowAim implements Listener {

    private List<Location> playerSpawnLocations = new ArrayList<>();
    private List<int[]> blockSpawnLocations = new ArrayList<>();
    private int numberOfBlocks = 3;
    private List<Material> blocks = new ArrayList<>();
    private String fileName = null;
    private int xOffset = 0;

    public static World world;
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
        //Get random map
        BowAim bowAim = bowAims.get(ThreadLocalRandom.current().nextInt(bowAims.size()));

        //Get Location
        Location location = new Location(world, 0, 100, 0);
        while (grid.contains(location)) {
            location.add(gridSpacing, 0, 0);
        }
        player.setLocation(location);
        grid.add(location);
        bowAim.xOffset = location.getBlockX();

        //Paste schematic
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
        //Teleport Player to random location
        p.teleport(bowAim.playerSpawnLocations.get(ThreadLocalRandom.current().nextInt(bowAim.playerSpawnLocations.size())));


        //Spawn blocks randomly
        bowAim.spawnRandomBlocks(bowAim.numberOfBlocks);

        player.setGamemode("BowAim");
        player.setBowAim(bowAim);
        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void endBowAimGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
        if (player == null || !player.getGamemode().equals("BowAim")) return;
        player.retrieveInventory();
        grid.remove(player.getLocation());
        Utils.cleanupArea(player.getLocation());
        p.teleport(Config.spawn);
    }

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private void spawnRandomBlocks(int amount) {
        for (int i = 0; i < amount; i++) {
            int[] coords = blockSpawnLocations.get(ThreadLocalRandom.current().nextInt(blockSpawnLocations.size()));
            int blockNumber = ThreadLocalRandom.current().nextInt((coords[3] - coords[0]) * (coords[4] - coords[1]) * (coords[5] - coords[2]));
            int count = 0;
            block: for (int x = coords[0]; x < coords[3]; x++) {
                for (int y = coords[1]; y < coords[4]; y++) {
                    for (int z = coords[2]; z < coords[5]; z++) {
                        if (count == blockNumber) {
                            world.setType(new Location(world, xOffset + x, y, z), blocks.get(ThreadLocalRandom.current().nextInt(blocks.size())));
                            break block;
                        }
                        count++;
                    }
                }
            }
        }
    }


    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || Objects.equals(player.getGamemode(), "BowAim")) return;
        Location to = e.getFrom();
        to.setPitch(e.getTo().getPitch());
        to.setYaw(e.getTo().getYaw());
        e.setTo(to);
    }

    @EventHandler
    public void onArrowLaunch(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !player.getGamemode().equals("BowAim")) return;

        player.increaseTotal();
    }

    @EventHandler
    public void onArrowHit(ProjectileHitEvent e) {
        Block b = e.getHitBlock();
        if (b == null || !(e.getEntity() instanceof Arrow arrow) || !(arrow.getShooter() instanceof Player p)) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !Objects.equals(player.getGamemode(), "BowAim")) return;
        Material type = b.getType();
        for (Material material : player.getBowAim().blocks) {
            if (material.equals(type)) {
                player.increaseSuccesses();
                p.playSound(p, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1, 1);
                b.setType(Material.AIR);
                arrow.remove();
                player.getBowAim().spawnRandomBlocks(1);
                return;
            }
        }

    }

}
