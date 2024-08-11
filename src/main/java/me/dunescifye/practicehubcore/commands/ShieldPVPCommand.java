package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.ShieldPVP.ShieldPVP;
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

                            ShieldPVP.challengePlayer(p, challenged);
                        })
                        .executesConsole((console, args) -> {
                            console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                        })
                    )
                )
                //Random
                .then(new LiteralArgument("start")
                )
                .then(new LiteralArgument("accept")
                )
                .then(new LiteralArgument("decline")
                )
                //Same as decline
                .then(new LiteralArgument("deny")
                )
                .withAliases(ShieldPVP.getCommandAliases())
                .withPermission("practicehub.command.shieldpvp")
                .register("practicehub");
        }
    }

}
