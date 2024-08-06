package me.dunescifye.practicehubcore.gamemodes.bowboost;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.*;
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

public class BowBoost implements Listener {

    public static String bowBoostCopyWorld = null;
    public static String bowBoost100mCopyWorld = null;
    public static String hitMessage = "&fHit!";

    public static void startBowBoostGame(Player p, String minigame) {
        PracticeHubPlayer player = new PracticeHubPlayer();

        //Setting up world
        Location teleportLocation;
        World world = null;
        switch (minigame) {
            case "practice" -> {
                PracticeHubCore.worldManager.cloneWorld(bowBoostCopyWorld, "bowBoost" + p.getName());
                world = Bukkit.getWorld("bowBoost" + p.getName());
                teleportLocation = new Location(world, 0, -60, 0);
            }
            case "100m" -> {
                PracticeHubCore.worldManager.cloneWorld(bowBoost100mCopyWorld, "bowBoost100m" + p.getName());
                world = Bukkit.getWorld("bowBoost100m" + p.getName());
                teleportLocation = new Location(world, 0, 100, 0);
                start100mGame(p);
            }
        }
        if (world == null) {
            p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
            return;
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
        p.sendMessage(Component.text("Starting!"));
        player.setGamemode("BowBoost");
        PracticeHubPlayer.linkedPlayers.put(p, player);

    }

    public static void endBowBridgeGame(Player p) {
        p.getInventory().clear();
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        p.getInventory().setContents(player.getSavedInventory());
        p.sendMessage(Component.text("Ended game!"));
        p.teleport(Config.spawn);
        PracticeHubCore.worldManager.deleteWorld("bowBoost" + p.getName());
    }

    private static void start100mGame(Player p) {

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
