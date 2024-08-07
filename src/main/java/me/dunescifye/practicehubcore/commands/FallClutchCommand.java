package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class FallClutchCommand {

    public static void register() {
        new CommandTree("fallclutch")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random"))
                .then(new LiteralArgument("waterbucket")
                )
                .then(new LiteralArgument("boat")
                )
                .then(new LiteralArgument("scaffolding"))
            )
            .withPermission("practicehub.command.fallclutch")
            .register("practicehub");
    }

}
