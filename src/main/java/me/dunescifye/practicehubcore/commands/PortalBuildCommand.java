package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.files.PortalBuildConfig;
import me.dunescifye.practicehubcore.gamemodes.PortalBuild;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

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
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    if (PortalBuildConfig.portalBuildWorld == null) {
                        p.sendMessage(Component.text("Portal Build is disabled!"));
                        return;
                    }

                    PortalBuild.endPortalBuildGame(p);
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
            )
            .withPermission("practicehub.command.portalbuild")
            .register("practicehub");
    }

}
