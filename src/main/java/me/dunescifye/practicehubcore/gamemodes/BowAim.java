package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.List;

public class BowAim {

    public static World bowAimWorld;
    public static HashMap<String, List<Location>> bowAimLocations;
    public static int bowAimGridSpacing;

    public static void startBowAimGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        //Setting up inventory
        ItemStack bow = new ItemStack(Material.BOW);
        bow.addEnchantment(Enchantment.ARROW_INFINITE, 1);
        ItemMeta meta = bow.getItemMeta();
        meta.setUnbreakable(true);
        bow.setItemMeta(meta);
        player.saveInventory(bow,
            new ItemStack(Material.ARROW));


        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

}
