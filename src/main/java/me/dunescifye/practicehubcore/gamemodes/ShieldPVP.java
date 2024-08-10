package me.dunescifye.practicehubcore.gamemodes;

import org.bukkit.entity.Player;

public class ShieldPVP {

    private static boolean enabled = true;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ShieldPVP.enabled = enabled;
    }

    public static void startGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        player.setGamemode("ShieldPVP");

        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
    }
}
