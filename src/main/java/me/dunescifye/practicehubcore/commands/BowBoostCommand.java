package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.BowBoost;
import net.kyori.adventure.text.Component;

public class BowBoostCommand {
    public static void register() {
        new CommandTree("bowboost")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Bow Boost is disabled
                    if (BowBoost.bowBoostCopyWorld == null) {
                        p.sendMessage(Component.text("Bow Boost is disabled!"));
                        return;
                    }

                    BowBoost.startBowBoostGame(p, "practice");
                })
                .then(new LiteralArgument("practice")
                    .executesPlayer((p, args) -> {
                        //Bow Boost is disabled
                        if (BowBoost.bowBoostCopyWorld == null) {
                            p.sendMessage(Component.text("Bow Boost is disabled!"));
                            return;
                        }

                        BowBoost.startBowBoostGame(p, "practice");
                    })
                )
                .then(new LiteralArgument("100m")
                    .executesPlayer((p, args) -> {
                        //Bow Boost is disabled
                        if (BowBoost.bowBoost100mCopyWorld == null) {
                            p.sendMessage(Component.text("100m Bow Boost is disabled!"));
                            return;
                        }

                        BowBoost.startBowBoostGame(p, "100m");
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    //Bow Boost is disabled
                    if (BowBoost.bowBoostCopyWorld == null) {
                        p.sendMessage(Component.text("Bow Boost is disabled!"));
                        return;
                    }
                    //Not in a game
                    PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                    if (player == null) {
                        p.sendMessage(Component.text("You are not in any game!"));
                        return;
                    }
                    BowBoost.endBowBridgeGame(p);
                })
            )
            .withPermission("practicehub.command.bowboost")
            .register("practicehub");
    }
}
