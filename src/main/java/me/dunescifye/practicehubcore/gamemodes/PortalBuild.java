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
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class PortalBuild {

    public static void startPortalBridgeGame(Player p) {
        Plugin plugin = PracticeHubCore.getPlugin();

        //Paste schematic
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "gamemodes/PortalBuild/Schematics/lava1.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        System.out.println("a");
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            System.out.println("b");
            clipboard = reader.read();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("c");
        try (EditSession editSession = WorldEdit.getInstance().newEditSession(BukkitAdapter.adapt(PortalBuildConfig.portalBuildWorld))) {
            System.out.println("d");
            Operation operation = new ClipboardHolder(clipboard)
                .createPaste(editSession)
                .to(BlockVector3.at(0, 100, 0))
                .build();
            Operations.complete(operation);
        } catch (WorldEditException e) {
            throw new RuntimeException(e);
        }

        //Setting up inventory
        PracticeHubCore.inventories.put(p, p.getInventory().getContents());
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
        inv.setItem(1, new ItemStack(Material.WATER_BUCKET));

        //Teleport Player
        p.teleport(new Location(PortalBuildConfig.portalBuildWorld, 0, 100, 0));

    }

}
