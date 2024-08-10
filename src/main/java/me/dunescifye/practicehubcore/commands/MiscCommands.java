package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Config;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class MiscCommands {

    private static String selfPingMessage;
    private static String otherPingMessage;
    private static String teleportMessage;
    private static Boolean pingCommandEnabled;
    private static Boolean spawnCommandEnabled;

    public static void register() {
        if (pingCommandEnabled) {
            new CommandTree("ping")
                .executesPlayer((p, args) -> {
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(selfPingMessage.replace("%ping%", String.valueOf(p.getPing()))));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(otherPingMessage.replace("%player%", p.getName()).replace("%ping%", String.valueOf(p.getPing()))));
                    })
                    .withPermission("practicehub.command.ping.other")
                )
                .withPermission("practicehub.command.ping")
                .register("practicehub");
        }

        if (spawnCommandEnabled) {
            new CommandTree("spawn")
                .executesPlayer((p, args) -> {
                    p.teleport(Config.spawn);
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(teleportMessage));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        p.teleport(Config.spawn);
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(teleportMessage));
                    })
                )
                .withPermission("practicehub.command.spawn")
                .register("practicehub");
        }
    }

    public static void setOtherPingMessage(String otherPingMessage) {
        MiscCommands.otherPingMessage = otherPingMessage;
    }

    public static void setSelfPingMessage(String selfPingMessage) {
        MiscCommands.selfPingMessage = selfPingMessage;
    }

    public static void setTeleportMessage(String teleportMessage) {
        MiscCommands.teleportMessage = teleportMessage;
    }

    public static void setPingCommandEnabled(Boolean pingCommandEnabled) {
        MiscCommands.pingCommandEnabled = pingCommandEnabled;
    }

    public static void setSpawnCommandEnabled(Boolean spawnCommandEnabled) {
        MiscCommands.spawnCommandEnabled = spawnCommandEnabled;
    }
}
