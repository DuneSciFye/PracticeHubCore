package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

@SuppressWarnings("DataFlowIssue")
public class ShieldPVPCommand {

    public static void register() {

        if (ShieldPVP.isEnabled()) {
            new CommandTree("shieldpvp")
                //Specific player to challenge
                .then(new LiteralArgument("challenge")
                    .then(new PlayerArgument("Player")
                        .executesPlayer((p, args) -> {
                            Player challenged = args.getUnchecked("Player");

                            ShieldPVP.challengePlayer(p, challenged);
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

                        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());

                        if (!player.hasChallenge()) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.noChallengeMessage));
                            return;
                        }

                        Player challenger = player.getFirstChallenger();
                        ShieldPVP game = new ShieldPVP(p, challenger);
                        game.start();
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                    .then(new PlayerArgument("Player")
                        .executesPlayer((p, args) -> {
                            if (!ShieldPVP.isEnabled()) {
                                p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.shieldPVPName)));
                                return;
                            }

                            Player challenger = args.getUnchecked("Player");
                            assert challenger != null;

                            PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());
                            PracticeHubPlayer challengerPlayer = PracticeHubPlayer.linkedPlayers.get(challenger.getUniqueId());

                            if (!player.hasChallengeFrom(challenger)) {
                                p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.noChallengeMessage));
                                return;
                            }
                            ShieldPVP game = new ShieldPVP(p, challenger);
                            game.start();
                            player.clearChallenges();
                            challengerPlayer.clearChallenges();

                        })
                        .executesConsole((console, args) -> {
                            console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                        })
                    )
                )
                .then(new LiteralArgument("decline")
                    .executesPlayer((p, args) -> {
                        if (!ShieldPVP.isEnabled()) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.shieldPVPName)));
                            return;
                        }

                        Player challenger = args.getUnchecked("Player");
                        assert challenger != null;

                        PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p.getUniqueId());
                        PracticeHubPlayer challengerPlayer = PracticeHubPlayer.linkedPlayers.get(challenger.getUniqueId());

                        if (!player.hasChallenge()) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.noChallengeMessage));
                            return;
                        }

                        ShieldPVP game = new ShieldPVP(p, challenger);
                        game.start();
                        player.clearChallenges();
                        challengerPlayer.clearChallenges();

                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                    .then(new PlayerArgument("Player")

                    )
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
