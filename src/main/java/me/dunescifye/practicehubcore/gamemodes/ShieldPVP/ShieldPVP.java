package me.dunescifye.practicehubcore.gamemodes.ShieldPVP;

import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ShieldPVP {

    private static boolean enabled = true;
    private static String[] commandAliases;

    public static void startGame(Player p) {
        PracticeHubPlayer player = new PracticeHubPlayer(p);
        player.setGamemode("ShieldPVP");

        PracticeHubPlayer.linkedPlayers.put(p, player);
    }

    public static void challengePlayer(Player challenger, Player challenged) {
        challenger.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengingMessage.replace("%player%", challenged.getName())));
        challenged.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.challengedMessage.replace("%player%", challenger.getName())));
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
}
