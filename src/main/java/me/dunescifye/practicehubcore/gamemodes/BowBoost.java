package me.dunescifye.practicehubcore.gamemodes;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.commands.BowBoostCommand;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.utils.Utils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
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

import java.io.File;
import java.time.Duration;
import java.time.Instant;
import java.util.logging.Logger;

public class BowBoost extends Gamemode implements Listener {

    private final Player p;
    private int hits = 0;

    public static String hitMessage;
    public static int startTime100m;
    public static ItemStack[] inventory;

    public BowBoost() {
        this.p = null;
    }
    public BowBoost(Player p) {
        this.p = p;
        PracticeHubCore.gamemodes.put(p.getUniqueId(), this);
    }

    public static void setup(PracticeHubCore plugin) {

        File configFile = new File(plugin.getDataFolder(), "gamemodes/BowBoost/config.yml");
        Logger logger = plugin.getLogger();

        if (!configFile.exists()) {
            configFile.getParentFile().mkdirs();
            plugin.saveResource("gamemodes/BowBoost/config.yml", false);
        }

        FileConfiguration config = YamlConfiguration.loadConfiguration(configFile);

        enabled = config.getBoolean("Enabled", true);
        if (!enabled) return;
        worldName = config.getString("BowBoostCopyWorld", "baseBowBoost");
        world = Bukkit.getWorld(worldName);
        if (world == null) {
            logger.severe("World \"" + worldName + "\" not found! Bow Boost gamemode disabled until fixed.");
            return;
        }
        startTime100m = config.getInt("StartTime100m", 5);
        commandAliases = config.getStringList("CommandAliases").toArray(new String[0]);
        hitMessage = config.getString("Messages.HitMessage", "&fHit!");
        BowBoostCommand.register();
        BowBoost.registerEvents(plugin);
    }

    @Override
    public void start() {
        //Setting up world
        World world;
        Location teleportLocation;
        /*
        if (minigame.equals("100m")) {
            PracticeHubCore.worldManager.cloneWorld(bowBoost100mCopyWorld, "bowBoost100m" + p.getName());
            String worldName = "bowBoost100m" + p.getName();
            world = Bukkit.getWorld("bowBoost100m" + p.getName());
            if (world == null) {
                p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
                return;
            }
            teleportLocation = new Location(world, 0, 100, 0);
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
        } else {
        }

         */
        PracticeHubCore.worldManager.cloneWorld(worldName, "bowBoost" + p.getName());
        world = Bukkit.getWorld("bowBoost" + p.getName());
        teleportLocation = new Location(world, 0, -60, 0);
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);
        p.teleport(teleportLocation);

        //Setting up inventory
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.INFINITY, 1);
        ItemMeta meta = bow.getItemMeta();
        meta.setUnbreakable(true);
        bow.setItemMeta(meta);
        Inventory inventory = p.getInventory();
        savedInventory = inventory.getContents();
        inventory.clear();
        inventory.addItem(bow, new ItemStack(Material.ARROW));
    }

    @Override
    public void end() {
        p.getInventory().clear();
        p.getInventory().setContents(savedInventory);
        p.sendMessage(Component.text("Ended game!"));
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld(worldName);
    }

    private void check100mWin(Player p, Instant startTime) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (p.getZ() > 102) {
                    cancel();
                    p.sendMessage(Component.text("You win!"));
                    p.sendMessage("Time: " + Utils.getFormattedTime(Duration.between(startTime, Instant.now())));
                    PracticeHubPlayer.linkedPlayers.remove(p.getUniqueId());
                    Bukkit.getScheduler().runTaskLater(PracticeHubCore.getPlugin(), () -> end(), 40L);
                }
            }
        }.runTaskTimer(PracticeHubCore.getPlugin(), 0L, 1L);
    }

    public static void registerEvents(PracticeHubCore plugin) {
        Bukkit.getPluginManager().registerEvents(new BowBoost(), plugin);
    }

    @EventHandler
    public static void onArrowHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Arrow arrow) || !(arrow.getShooter() instanceof Player player) || player != e.getEntity()) return;
        if (!(PracticeHubCore.gamemodes.get(player.getUniqueId()) instanceof BowBoost bowBoost)) return;

        bowBoost.hits++;
        arrow.remove();
        player.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(hitMessage));
    }

    @EventHandler
    public static void onArrowLaunch(EntityShootBowEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());
        if (player == null || !player.getGamemode().equals("BowBoost")) return;

        player.increaseTotal();
        p.sendMessage("Your pitch was " + p.getPitch());
        p.sendMessage("Your bow force was " + e.getForce());

    }

}
