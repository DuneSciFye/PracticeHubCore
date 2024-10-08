package me.dunescifye.practicehubcore.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.utils.ClicksPerSecond;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Placeholders extends PlaceholderExpansion {
    @Override
    public @NotNull String getIdentifier() {
        return "practicehub";
    }

    @Override
    public @NotNull String getAuthor() {
        return "DuneSciFye";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String arg) {
        if (!(player instanceof Player p))
            return null;

        String[] args = arg.split("_", 2);
        switch (args[0]) {
            case "leftcps" -> {
                return String.valueOf(ClicksPerSecond.leftClicksPerSecond.get(p).size());
            }
            case "rightcps" -> {
                return String.valueOf(ClicksPerSecond.rightClicksPerSecond.get(p).size());
            }
            case "lavaSchem" -> {
                PracticeHubPlayer practiceHubPlayer = PracticeHubPlayer.linkedPlayers.get(p);
                if (practiceHubPlayer == null) return "Not in game.";
                return practiceHubPlayer.getLavaSchem();
            }
            case "success" -> {
                PracticeHubPlayer practiceHubPlayer = PracticeHubPlayer.linkedPlayers.get(p);
                if (practiceHubPlayer == null) return "Not in game.";
                return String.valueOf(practiceHubPlayer.getSuccesses());
            }
            case "total" -> {
                PracticeHubPlayer practiceHubPlayer = PracticeHubPlayer.linkedPlayers.get(p);
                if (practiceHubPlayer == null) return "Not in game.";
                return String.valueOf(practiceHubPlayer.getTotal());
            }
            case "gamemode" -> {
                PracticeHubPlayer practiceHubPlayer = PracticeHubPlayer.linkedPlayers.get(p);
                if (practiceHubPlayer == null) return "Not in game.";
                return practiceHubPlayer.getGamemode();
            }

        }
        return null;
    }
}
