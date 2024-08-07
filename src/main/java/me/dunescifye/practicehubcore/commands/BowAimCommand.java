package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.BowAim;

public class BowAimCommand {

    public static void register() {
        new CommandTree("bowaim")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    BowAim.startBowAimGame(p);
                })
            )
            .withPermission("commandutils.command.bowaim")
            .register("practicehub");
    }

}
