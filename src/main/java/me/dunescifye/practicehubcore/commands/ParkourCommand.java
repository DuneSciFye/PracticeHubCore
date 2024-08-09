package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class ParkourCommand {

    public static void register() {
        new CommandTree("parkour")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random")
                    .executesPlayer((p, args) -> {

                    })
                )
            )
            .withPermission("practicehub.command.parkour")
            .register("practicehub");
    }

}
