package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.gamemodes.bowboost.BowBoost;
import net.kyori.adventure.text.Component;

public class BowBoostCommand {
    public static void register() {
        new CommandTree("bowboost")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Bow Boost is disabled
                    if (BridgeConfig.bridgeCopyWorld == null) {
                        p.sendMessage(Component.text("Bridge is disabled!"));
                        return;
                    }

                    BowBoost.startBowBoostGame(p);
                })
            )
            .withPermission("practicehub.command.bowboost")
            .register("practicehub");
    }
}
