package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ShieldPVPCommand {

    public static void register() {
        new CommandTree("shieldpvp")
            //Specific player to challenge
            .then(new LiteralArgument("challenge")
                .then(new PlayerArgument("Player")
                    .executesPlayer((p, args) -> {

                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
            )
            //Random
            .then(new LiteralArgument("start")
            )
            .then(new LiteralArgument("accept")
            )
            .withPermission("practicehub.command.shieldpvp")
            .register("practicehub");
    }

}
