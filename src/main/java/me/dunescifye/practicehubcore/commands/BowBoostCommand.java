package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.bowboost.BowBoost;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

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

                    BowBoost.startBowBoostGame(p);
                })
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
