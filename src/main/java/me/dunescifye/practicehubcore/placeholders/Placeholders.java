package me.dunescifye.practicehubcore.placeholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
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
    public @Nullable String onRequest(OfflinePlayer p, @NotNull String arg) {
        String[] args = arg.split("_", 2);
        if (args[0].equals("cps")) {
            return String.valueOf(Bridge.cps.get((Player) p));
        }
        return null;
    }
}
