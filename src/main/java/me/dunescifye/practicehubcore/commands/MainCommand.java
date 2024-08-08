package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;

public class MainCommand {

    public static void register() {
        new CommandTree("practicehub")
            .then(new LiteralArgument("reload")
                .executes((sender, args) -> {
                    PracticeHubCore.setupFiles();
                    sender.sendMessage("Reloaded Config!");
                })
            )
            .withAliases("phub")
            .withPermission("practicehub.command.practicehub")
            .register("practicehub");
    }

}
