package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class BridgeCommand {

    public static void register() {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Gamemode disabled
                    if (Bridge.getCopyWorldName() == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bridgeName)));
                        return;
                    }
                    Bridge.startBridgeGame(p, "practice");
                })
                .then(new LiteralArgument("practice")
                    .executesPlayer((p, args) -> {
                        Bridge.startBridgeGame(p, "practice");
                    })
                    .withPermission("practicehub.command.bridge.practice")
                    .then(new PlayerArgument("Player")
                        .executes((sender, args) -> {
                            if (Bridge.getCopyWorldName() == null) {
                                sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bridgeName)));
                                return;
                            }
                            Player p = args.getUnchecked("Player");
                            assert p != null;
                            Bridge.startBridgeGame(p, "practice");
                        })
                        .withPermission("practicehub.command.bridge.practice.other")
                    )
                )
                .then(new LiteralArgument("100m")
                    .executesPlayer((p, args) -> {
                        Bridge.startBridgeGame(p, "100m");
                    })
                    .withPermission("practicehub.command.bridge.100m")
                    .then(new PlayerArgument("Player")
                        .executes((sender, args) -> {
                            if (Bridge.getCopyWorldName() == null) {
                                sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bridgeName)));
                                return;
                            }
                            Player p = args.getUnchecked("Player");
                            assert p != null;
                            Bridge.startBridgeGame(p, "100m");
                        })
                        .withPermission("practicehub.command.bridge.100m.other")
                    )
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    //Bridge is disabled
                    if (Bridge.getCopyWorldName() == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bridgeName)));
                        return;
                    }
                    //Not in a game
                    PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                    if (player == null) {
                        p.sendMessage(Component.text("You are not in any game!"));
                        return;
                    }
                    Bridge.endBridgeGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        //Bridge is disabled
                        if (Bridge.getCopyWorldName() == null) {
                            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bridgeName)));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        //Not in a game
                        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                        if (player == null) {
                            p.sendMessage(Component.text("You are not in any game!"));
                            return;
                        }
                        Bridge.endBridgeGame(p);
                    })
                )
            )
            .withPermission("practicehub.command.bridge")
            .register("practicehub");
    }
}
