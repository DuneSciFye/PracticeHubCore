package me.dunescifye.practicehubcore.gamemodes;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
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
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.session.ClipboardHolder;
import com.sk89q.worldedit.world.block.BlockTypes;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.PortalCreateEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class PortalBuild implements Listener {

    public static HashMap<Player, String> lavaSchem = new HashMap<>();

    public static void startPortalBridgeGame(Player p) {
        Plugin plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

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
                .to(BlockVector3.at(0, 100, 0))
                .copyBiomes(false)
                .copyEntities(false)
                .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            p.sendMessage("An error occurred! Report to an administrator!");
            throw new RuntimeException(e);
        }
        lavaSchem.put(p, fileName);

        //Setting up inventory
        PracticeHubCore.inventories.put(p, p.getInventory().getContents());
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(1, new ItemStack(Material.WATER_BUCKET));
        inv.setItem(2, new ItemStack(Material.IRON_PICKAXE));
        inv.setItem(3, new ItemStack(Material.FLINT_AND_STEEL));

        //Teleport Player
        List<Location> teleportLoc = PortalBuildConfig.lavaPools.get(fileName);
        p.teleport(teleportLoc.get(ThreadLocalRandom.current().nextInt(teleportLoc.size())));

        //Other
        PracticeHubCore.gamemode.put(p, "PortalBuild");

    }

    public void portalCreateHandler(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPortalCreateEvent(PortalCreateEvent e) {
        if (e.getReason() != PortalCreateEvent.CreateReason.FIRE) return;
        if (e.getEntity() instanceof Player p) {
            if (!PracticeHubCore.gamemode.remove(p).equals("PortalBuild")) return;

            p.sendMessage("You win!");
            p.getInventory().clear();
            p.getInventory().setContents(PracticeHubCore.inventories.get(p));
            p.teleport(Config.spawn);
            lavaSchem.remove(p);

            //Cleanup schem area
            try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PortalBuildConfig.portalBuildWorld))) {
                CuboidRegion region = new CuboidRegion(new BlockVector3(-10, -64, -10), new BlockVector3(100, 320, 100));
                editSession.setBlocks(region, BlockTypes.AIR.getDefaultState());
            } catch (MaxChangedBlocksException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
