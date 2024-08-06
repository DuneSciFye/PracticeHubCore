package me.dunescifye.practicehubcore.gamemodes.bowboost;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class BowBoost implements Listener {

    public static String bowBoostCopyWorld = null;
    public static String bowBoost100mCopyWorld = null;
    public static String hitMessage = "&fHit!";
    public static int startTime100m = 5;

    public static void startBowBoostGame(Player p, String minigame) {
        PracticeHubPlayer player = new PracticeHubPlayer();

        //Setting up world
        World world;
        Location teleportLocation;
        switch (minigame) {
            case "100m" -> {
                PracticeHubCore.worldManager.cloneWorld(bowBoost100mCopyWorld, "bowBoost100m" + p.getName());
                player.setWorldName("bowBoost100m" + p.getName());
                world = Bukkit.getWorld("bowBoost100m" + p.getName());
                player.setWorld(world);
                if (world == null) {
                    p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
                    return;
                }
                teleportLocation = new Location(player.getWorld(), 0, 100, 0);
                new BukkitRunnable() {
                    int seconds = startTime100m;

                    @Override
                    public void run() {
                        if (seconds < 1) {
                            cancel();
                            //Remove glass
                            for (int x = -5; x < 7; x++) {
                                for (int y = 100; y < 129; y++) {
                                    Block block = world.getBlockAt(x, y, 3);
                                    block.setType(Material.AIR);
                                }
                            }
                            p.sendMessage(Component.text("GO!"));
                            check100mWin(p, Instant.now());
                            return;
                        }

                        p.sendMessage(Component.text("Starting in " + seconds + "..."));

                        seconds--;
                    }
                }.runTaskTimer(PracticeHubCore.getPlugin(), 20L, 20L);
            }
            default -> {
                PracticeHubCore.worldManager.cloneWorld(bowBoostCopyWorld, "bowBoost" + p.getName());
                player.setWorldName("bowBoost" + p.getName());
                world = Bukkit.getWorld("bowBoost" + p.getName());
                teleportLocation = new Location(world, 0, -60, 0);
            }
        }
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);
        p.teleport(teleportLocation);

        //Setting up inventory
        player.setSavedInventory(p.getInventory().getContents());
        Inventory inv = p.getInventory();
        inv.clear();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = bow.getItemMeta();
        meta.setUnbreakable(true);
        bow.setItemMeta(meta);
        inv.setItem(0, bow);
        inv.setItem(1, new ItemStack(Material.ARROW));

        //Everything works
        player.setGamemode("BowBoost");
        PracticeHubPlayer.linkedPlayers.put(p, player);

    }

    public static void endBowBridgeGame(Player p) {
        p.getInventory().clear();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        p.getInventory().setContents(player.getSavedInventory());
        p.sendMessage(Component.text("Ended game!"));
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld(player.getWorldName());
    }

    private static void check100mWin(Player p, Instant startTime) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getZ() > 102) {
                    cancel();
                    p.sendMessage(Component.text("You win!"));
                    endBowBridgeGame(p);
                    p.sendMessage("Time: " + Utils.getFormattedTime(Duration.between(startTime, Instant.now())));
                    PracticeHubPlayer.linkedPlayers.remove(p);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 1L);
    }

    public void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Arrow arrow) || !(arrow.getShooter() instanceof Player p) || p != e.getEntity()) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !player.getGamemode().equals("BowBoost")) return;

        player.hitArrow();
        arrow.remove();
        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(hitMessage));
    }

    @EventHandler
    public void onArrowLaunch(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (player == null || !player.getGamemode().equals("BowBoost")) return;

        player.launchArrow();
        p.sendMessage("Your pitch was " + p.getPitch());
        p.sendMessage("Your bow force was " + e.getForce());

    }

}
