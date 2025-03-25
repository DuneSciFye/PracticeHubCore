package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.gamemodes.BowBoost;

public class MainCommand {

    public static void register() {
        new CommandTree("practicehub")
            .then(new LiteralArgument("reload")
                .executes((sender, args) -> {
                    BowBoost.setup(PracticeHubCore.getPlugin());
                    sender.sendMessage("Reloaded Practice!");
                })
            )
            .withAliases("phub")
            .withPermission("practicehub.command.practicehub")
            .register("practicehub");
    }

}
