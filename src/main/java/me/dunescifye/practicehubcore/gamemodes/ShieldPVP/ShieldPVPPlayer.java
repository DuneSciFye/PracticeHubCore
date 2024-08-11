package me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import org.bukkit.entity.Player;

public class ShieldPVPPlayer extends PracticeHubPlayer {

    public ShieldPVPPlayer(Player p) {
        super(p);

    }

    private void challengePlayer(Player p) {
        p.sendMessage();

    }

}
