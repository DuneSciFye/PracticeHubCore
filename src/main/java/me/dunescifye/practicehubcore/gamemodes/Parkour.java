package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.entity.Player;

public class Parkour {

    public static void startGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);

        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);

    }

}
