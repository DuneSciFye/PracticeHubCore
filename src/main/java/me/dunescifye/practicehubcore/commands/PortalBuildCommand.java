package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.PortalBuild;

public class PortalBuildCommand {

    public static void register() {
        new CommandTree("portalbuild")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    PortalBuild.startPortalBridgeGame(p);
                })
            )
            .withPermission("practicehub.command.portalbuild")
            .register("practicehub");
    }

}
