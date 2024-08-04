package me.dunescifye.practicehubcore.gamemodes;

import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormat;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;

public class PortalBuild {

    public static void startPortalBridgeGame(Player p) {
        Plugin plugin = PracticeHubCore.getPlugin();

        //Paste schematic
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "schematics/lava1.schem");

        ClipboardFormat format = ClipboardFormats.findByFile(file);
        try (ClipboardReader reader = format.getReader(new FileInputStream(file))) {
            clipboard = reader.read();
        }
        /* use the clipboard here */

        //Setting up inventory
        PracticeHubCore.inventories.put(p, p.getInventory().getContents());
        Inventory inv = p.getInventory();
        inv.clear();
        inv.setItem(0, new ItemStack(Material.COBBLESTONE, 64));
        p.getInventory().setHeldItemSlot(0);

    }

}
