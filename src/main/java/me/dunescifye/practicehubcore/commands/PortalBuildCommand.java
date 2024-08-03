package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class PortalBuildCommand {

    public static void register() {
        new CommandTree("portalbuild")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {

                })
            )
            .withPermission("practicehub.command.portalbuild")
            .register("practicehub");
    }

}
