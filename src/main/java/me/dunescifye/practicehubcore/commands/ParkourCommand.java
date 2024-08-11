package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.Parkour;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public class ParkourCommand {

    public static void register() {
        new CommandTree("parkour")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("1blockneo")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "1 Block Neo");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("2blockneo")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "2 Block Neo");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("3blockneo")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "3 Block Neo");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("easy")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("medium")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("hard")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    Parkour.endGame(p);
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
            )
            .withPermission("practicehub.command.parkour")
            .register("practicehub");
    }

}
