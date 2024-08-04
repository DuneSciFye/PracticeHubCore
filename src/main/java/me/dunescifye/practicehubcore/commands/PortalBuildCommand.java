package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import me.dunescifye.practicehubcore.gamemodes.PortalBuild;
import net.kyori.adventure.text.Component;

public class PortalBuildCommand {

    public static void register() {
        new CommandTree("portalbuild")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Gamemode disabled
                    if (PortalBuildConfig.portalBuildWorld == null) {
                        p.sendMessage(Component.text("Portal Build is disabled!"));
                        return;
                    }

                    PortalBuild.startPortalBridgeGame(p);
                })
            )
            .withPermission("practicehub.command.portalbuild")
            .register("practicehub");
    }

}