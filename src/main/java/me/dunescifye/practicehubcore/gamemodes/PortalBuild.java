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
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import me.dunescifye.practicehubcore.utils.TimedBlock;
import me.dunescifye.practicehubcore.utils.Utils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class PortalBuild implements Listener {

    public static void startPortalBridgeGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        Plugin plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        //Get Location
        Location location = new Location(PortalBuildConfig.portalBuildWorld, 0, 100, 0);
        while (PortalBuildConfig.grid.contains(location)) {
            location.add(PortalBuildConfig.gridSpacing, 0, 0);
        }
        player.setLocation(location);
        PortalBuildConfig.grid.add(location);

        //Paste schematic
        List<String> schematics = new ArrayList<>(PortalBuildConfig.lavaPools.keySet());
        String fileName = schematics.get(ThreadLocalRandom.current().nextInt(schematics.size()));
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "gamemodes/PortalBuild/Schematics/" + fileName);
        ClipboardFormat format = ClipboardFormats.findByFile(file);

        if (format == null) {
            logger.severe("Schematic " + fileName + " not found for Portal Build gamemode.");
            return;
        }

        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        } catch (IOException e) {
            p.sendMessage("There was nothing on the clipboard! Report to an administrator!");
            throw new RuntimeException(e);
        }
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PortalBuildConfig.portalBuildWorld))) {
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
        player.saveInventory(new ItemStack(Material.COBBLESTONE, 64),
            new ItemStack(Material.WATER_BUCKET),
            new ItemStack(Material.IRON_PICKAXE),
            new ItemStack(Material.FLINT_AND_STEEL));

        //Teleport Player
        List<Location> teleportLoc = PortalBuildConfig.lavaPools.get(fileName);
        p.teleport(teleportLoc.get(ThreadLocalRandom.current().nextInt(teleportLoc.size())));

        //Other
        player.setGamemode("PortalBuild");
        player.setLavaSchem(fileName);
        player.setStartTime(Instant.now());
        PracticeHubPlayer.linkedPlayers.put(p, player);

    }

    public static void endPortalBuildGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        p.sendMessage("You win!");
        player.retrieveInventory();
        p.teleport(Config.spawn);

        //Cleanup schem area
        Location location = player.getLocation();
            /*
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PortalBuildConfig.portalBuildWorld))) {
                CuboidRegion region = new CuboidRegion(BlockVector3.at(-10, -64, -10), BlockVector3.at(100, 320, 100));
                editSession.setBlocks(region, BlockTypes.AIR.getDefaultState());
            } catch (MaxChangedBlocksException ex) {
                throw new RuntimeException(ex);
            }
             */
        Block origin = PortalBuildConfig.portalBuildWorld.getBlockAt(location);
        for (int x = -100; x < 100; x++) {
            for (int y = -64; y < 320; y++) {
                for (int z = -100; z < 100; z++) {
                    Block relative = origin.getRelative(x, y, z);
                    relative.setType(Material.AIR);
                }
            }
        }

        //Get times
        LinkedList<TimedBlock> blocks = player.getPlacedBlocks();

        p.sendMessage(Component.text("Total time of " + Utils.getFormattedTime(Duration.between(player.getStartTime(), Instant.now()))));
        p.sendMessage(Component.text("Time from first block place: " + Utils.getFormattedTime(Duration.between(blocks.getFirst().getTime(), Instant.now()))));
        p.sendMessage(Component.text("Time for portal frame build: " + Utils.getFormattedTime(Duration.between(blocks.getFirst().getTime(), blocks.getLast().getTime()))));

        //

        PracticeHubPlayer.linkedPlayers.remove(p);
        PortalBuildConfig.grid.remove(location);

    }

    public void portalCreateHandler(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPortalCreateEvent(PortalCreateEvent e) {
        if (e.getReason() != PortalCreateEvent.CreateReason.FIRE) return;
        if (e.getEntity() instanceof Player p) {
            PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
            if (!player.getGamemode().equals("PortalBuild")) return;
            e.setCancelled(true);
            endPortalBuildGame(p);

        }
    }

}
