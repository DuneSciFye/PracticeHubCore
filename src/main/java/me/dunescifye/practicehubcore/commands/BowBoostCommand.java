package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.Gamemode;
import me.dunescifye.practicehubcore.gamemodes.BowBoost;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

@SuppressWarnings("DataFlowIssue")
public class BowBoostCommand {
    public static void register() {
        new CommandTree("bowboost")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    new BowBoost(p).start();
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
                .then(new LiteralArgument("practice")
                    .executesPlayer((p, args) -> {
                        new BowBoost(p).start();
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("100m")
                    .executesPlayer((p, args) -> {
                        //bowBoost.startBowBoostGame(p, "100m");
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    Gamemode gamemode = PracticeHubCore.gamemodes.get(p.getUniqueId());
                    if (gamemode == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.notInGame.replace("%gamemode%", Messages.bowBoostName)));
                    } else gamemode.end();
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        Player p = args.getUnchecked("Player");
                        //Not in a game
                        Gamemode gamemode = PracticeHubCore.gamemodes.get(p.getUniqueId());
                        if (gamemode == null) {
                            p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.notInGame.replace("%gamemode%", Messages.bowBoostName)));
                        } else gamemode.end();
                    })
                )
            )
            .withPermission("practicehub.command.bowboost")
            .withAliases(BowBoost.commandAliases)
            .register("practicehub");
    }
}
