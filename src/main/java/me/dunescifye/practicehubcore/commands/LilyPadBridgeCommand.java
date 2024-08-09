package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.LilyPadBridge;

public class LilyPadBridgeCommand {

    public static void setup() {
        new CommandTree("lilypadbridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    LilyPadBridge.startGame(p);
                })
            )
            .withPermission("practicehub.command.lilypadbridge")
            .register("practicehub");
    }

}
