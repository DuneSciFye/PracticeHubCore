package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.files.Messages;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class MiscCommands {

    private static Boolean pingCommandEnabled;
    private static Boolean spawnCommandEnabled;

    public static void register() {
        if (pingCommandEnabled) {
            new CommandTree("ping")
                .executesPlayer((p, args) -> {
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.selfPingMessage.replace("%ping%", String.valueOf(p.getPing()))));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.otherPingMessage.replace("%player%", p.getName()).replace("%ping%", String.valueOf(p.getPing()))));
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
                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.teleportMessage));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        p.teleport(Config.spawn);
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.teleportMessage));
                    })
                )
                .withPermission("practicehub.command.spawn")
                .register("practicehub");
        }
    }

    public static void setPingCommandEnabled(Boolean pingCommandEnabled) {
        MiscCommands.pingCommandEnabled = pingCommandEnabled;
    }

    public static void setSpawnCommandEnabled(Boolean spawnCommandEnabled) {
        MiscCommands.spawnCommandEnabled = spawnCommandEnabled;
    }
}
