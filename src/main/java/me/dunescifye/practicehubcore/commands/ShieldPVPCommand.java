package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class ShieldPVPCommand {

    public static void register() {

        if (ShieldPVP.isEnabled()) {
            new CommandTree("shieldpvp")
                //Specific player to challenge
                .then(new LiteralArgument("challenge")
                    .then(new PlayerArgument("Player")
                        .executesPlayer((p, args) -> {
                            if (!ShieldPVP.isEnabled()) {
                                p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.shieldPVPName)));
                                return;
                            }
                            Player challenged = args.getUnchecked("Player");
                            assert challenged != null;

                            player.challengePlayer(challenged);
                            PracticeHubPlayer.linkedPlayers.put(p.getUniqueId(), player);
                        })
                        .executesConsole((console, args) -> {
                            console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                        })
                    )
                )
                //Random
                .then(new LiteralArgument("start")
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("accept")
                    .executesPlayer((p, args) -> {
                        if (!ShieldPVP.isEnabled()) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.shieldPVPName)));
                            return;
                        }

                        if (!ShieldPVP.incomingChallenges.containsKey(p)) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.noChallengeMessage));
                            return;
                        }

                        Player challenger = ShieldPVP.incomingChallenges.get(p);
                        ShieldPVP.startGame(p, challenger);

                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("decline")
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                //Same as decline
                .then(new LiteralArgument("deny")
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                //Cancelling a sent challenge
                .then(new LiteralArgument("cancel")
                    .executesPlayer((p, args) -> {

                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .withAliases(ShieldPVP.getCommandAliases())
                .withPermission("practicehub.command.shieldpvp")
                .register("practicehub");
        }
    }

}
