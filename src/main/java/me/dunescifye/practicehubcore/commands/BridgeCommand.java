package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class BridgeCommand {

    public static void register() {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Gamemode disabled
                    if (BridgeConfig.bridgeCopyWorld == null) {
                        p.sendMessage(Component.text("Bridge is disabled!"));
                        return;
                    }
                    Bridge.startBridgeGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (BridgeConfig.bridgeCopyWorld == null) {
                            sender.sendMessage(Component.text("Bridge is disabled!"));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        Bridge.startBridgeGame(p);
                    })
                    .withPermission("practicehub.command.bridge.other")
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    //Bridge is disabled
                    if (BridgeConfig.bridgeCopyWorld == null) {
                        p.sendMessage(Component.text("Bridge is disabled!"));
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
                        if (BridgeConfig.bridgeCopyWorld == null) {
                            sender.sendMessage(Component.text("Bridge is disabled!"));
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
