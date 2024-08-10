package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.Parkour;

public class ParkourCommand {

    public static void register() {
        new CommandTree("parkour")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                )
                .then(new LiteralArgument("easy")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                )
                .then(new LiteralArgument("medium")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                )
                .then(new LiteralArgument("hard")
                    .executesPlayer((p, args) -> {
                        Parkour.startGame(p, "random");
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    Parkour.endGame(p);
                })
            )
            .withPermission("practicehub.command.parkour")
            .register("practicehub");
    }

}
