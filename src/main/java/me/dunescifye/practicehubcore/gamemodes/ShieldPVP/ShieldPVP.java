package me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class ShieldPVP {

    private static boolean enabled = true;
    private static String[] commandAliases;
    public static HashMap<Player, Player> outgoingChallenges = new HashMap<>();
    public static HashMap<Player, Player> incomingChallenges = new HashMap<>();
    private static String worldName;
    private static World world;

    public static void startGame(Player p, Player p2) {
        PracticeHubPlayer player = new PracticeHubPlayer(p), player2 = new PracticeHubPlayer(p2);
        player.setGamemode("ShieldPVP");
        player2.setGamemode("ShieldPVP");



        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void challengePlayer(Player challenger, Player challenged) {
        challenger.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengingMessage.replace("%player%", challenged.getName())));
        challenged.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengedMessage.replace("%player%", challenger.getName())));

        outgoingChallenges.put(challenger, challenged);
        incomingChallenges.put(challenged, challenger);
    }

    public static void endGame(Player p) {
        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.remove(p);
    }


    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ShieldPVP.enabled = enabled;
    }

    public static String[] getCommandAliases() {
        return commandAliases;
    }

    public static void setCommandAliases(String[] commandAliases) {
        ShieldPVP.commandAliases = commandAliases;
    }

    public static World getWorld() {
        return world;
    }

    public static String getWorldName() {
        return worldName;
    }

    public static void setWorldName(String worldName) {
        ShieldPVP.worldName = worldName;
    }

    public static void setWorld(World world) {
        ShieldPVP.world = world;
    }
}
