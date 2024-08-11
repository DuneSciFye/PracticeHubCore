package me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

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
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class ShieldPVP {

    private static boolean enabled = true;
    private static String[] commandAliases;
    public static HashMap<Player, Player> outgoingChallenges = new HashMap<>();
    public static HashMap<Player, Player> incomingChallenges = new HashMap<>();
    private static String worldName;
    private static World world;
    private static HashMap<String, List<Location>> schematics = new HashMap<>();
    private static int spacing;
    private static ArrayList<Location> grid = new ArrayList<>();

    public static void startGame(Player p, Player p2) {
        Plugin plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        PracticeHubPlayer player = new PracticeHubPlayer(p), player2 = new PracticeHubPlayer(p2);
        player.setGamemode("ShieldPVP");
        player2.setGamemode("ShieldPVP");

        //Get Location
        Location location = new Location(world, 0, 100, 0);
        while (grid.contains(location)) {
            location.add(spacing, 0, 0);
        }
        player.setLocation(location);
        grid.add(location);

        //Past Schematic
        List<String> schematicsList = new ArrayList<>(schematics.keySet());
        String fileName = schematicsList.get(ThreadLocalRandom.current().nextInt(schematicsList.size()));
        Clipboard clipboard;
        File file = new File(plugin.getDataFolder(), "gamemodes/ShieldPVP/Schematics/" + fileName);
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
        player.saveInventory();
        player2.saveInventory();
        PlayerInventory inventory = p.getInventory();
        inventory.setItemInOffHand(new ItemStack(Material.SHIELD));
        inventory.setItem(0, new ItemStack(Material.IRON_AXE));
        inventory = p2.getInventory();
        inventory.setItemInOffHand(new ItemStack(Material.SHIELD));
        inventory.setItem(0, new ItemStack(Material.IRON_AXE));

        //Teleport Player
        List<Location> teleportLoc = PortalBuildConfig.lavaPools.get(fileName);
        p.teleport(teleportLoc.get(ThreadLocalRandom.current().nextInt(teleportLoc.size())));
        p2.teleport(teleportLoc.get(ThreadLocalRandom.current().nextInt(teleportLoc.size())));

        //Other
        player.setGamemode("Shield PVP");
        player.setStartTime(Instant.now());
        PracticeHubPlayer.linkedPlayers.put(p, player);

    }

    public static void challengePlayer(Player challenger, Player challenged) {
        challenger.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengingMessage.replace("%player%", challenged.getName())));
        challenged.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengedMessage.replace("%player%", challenger.getName())));

        outgoingChallenges.put(challenger, challenged);
        incomingChallenges.put(challenged, challenger);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
    }


    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ShieldPVP.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        ShieldPVP.commandAliases = commandAliases;
    }

    public static World getWorld() {
        return world;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void setWorldName(String worldName) {
        ShieldPVP.worldName = worldName;
    }

    public static void setWorld(World world) {
        ShieldPVP.world = world;
    }

    public static void addSchematic(String fileName, List<Location> spawnLocations) {
        schematics.put(fileName, spawnLocations);
    }

    public static void setSpacing(int spacing) {
        this.spacing = spacing;
    }
}
