package me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class ShieldPVPPlayer extends PracticeHubPlayer {
    private static ArrayList<UUID> sentChallenges;
    private static ArrayList<UUID> receivedChallenges;

    public ShieldPVPPlayer(Player p) {
        super(p);

    }

    public void challengePlayer(Player p) {
        sentChallenges.add(p.getUniqueId());
    }

    public boolean hasChallenge(Player p) {
        return receivedChallenges.contains(p.getUniqueId());
    }

}
