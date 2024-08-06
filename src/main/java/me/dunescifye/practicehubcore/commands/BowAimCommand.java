package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;

public class BowAimCommand {

    public static void register() {
        new CommandTree("bowaim")
            .then(new LiteralArgument("start")
            )
            .withPermission("commandutils.command.bowaim")
            .register("practicehub");
    }

}
