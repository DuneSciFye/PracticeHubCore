package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class LilyPadBridgeCommand {

    public static void setup() {
        new CommandTree("lilypadbridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {

                })
            )
            .withPermission("practicehub.command.lilypadbridge")
            .register("practicehub");
    }

}
