package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.entity.Player;

public class BowAim {

    public static void startBowAimGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer();



        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

}
