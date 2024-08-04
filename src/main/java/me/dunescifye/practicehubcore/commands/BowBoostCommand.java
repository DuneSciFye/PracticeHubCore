package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class BowBoostCommand {
    public static void register() {
        new CommandTree("bowboost")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {

                })
            )
            .withPermission("practicehub.command.bowboost")
            .register("practicehub");
    }
}
