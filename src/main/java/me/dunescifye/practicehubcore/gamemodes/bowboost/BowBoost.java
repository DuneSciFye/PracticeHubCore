package me.dunescifye.practicehubcore.gamemodes.bowboost;

import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class BowBoost implements Listener {

    public static String bowBoostCopyWorld = null;

    public static void startBowBoostGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer();

        //Setting up world
        PracticeHubCore.worldManager.cloneWorld(bowBoostCopyWorld, "bowBoost" + p.getName());

        //Teleport player
        World world = Bukkit.getWorld("bowBoost" + p.getName());
        if (world == null) {
            p.sendMessage(Component.text("Invalid world. Please contact an administrator."));
            return;
        }
        p.teleport(new Location(world, 0, 100, 0));
        p.setGameMode(GameMode.SURVIVAL);
        p.setFoodLevel(20);

        //Setting up inventory
        player.setSavedInventory(p.getInventory().getContents());
        Inventory inv = p.getInventory();
        inv.clear();
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_KNOCKBACK, 2);
        ItemMeta meta = bow.getItemMeta();
        meta.setUnbreakable(true);
        bow.setItemMeta(meta);
        inv.setItem(0, bow);

        //Everything works
        p.sendMessage(Component.text("Starting!"));
        player.setGamemode("BowBoost");

    }

    @EventHandler
    public void onArrowHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player p)) return;
        if (p != e.getEntity()) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (!player.getGamemode().equals("BowBoost")) return;

        player.hitArrow();
    }

    @EventHandler
    public void onArrowLaunch(ProjectileLaunchEvent e) {
        if (!(e.getEntity().getShooter() instanceof Player p)) return;
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
        if (!player.getGamemode().equals("BowBoost")) return;

        player.launchArrow();
    }

}
