package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.entity.Player;

public class LilyPadBridge {

    public static void startGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        player.setGamemode("LilyPadBridge");
    }

    public void endGame() {

    }

}
