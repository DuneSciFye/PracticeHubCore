package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.BowAim;
import me.dunescifye.practicehubcore.gamemodes.PracticeHubPlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class BowAimCommand {

    public static void register() {
        new CommandTree("bowaim")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    if (BowAim.world == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bowAimName)));
                        return;
                    }
                    BowAim.startBowAimGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (BowAim.world == null) {
                            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bowAimName)));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        BowAim.startBowAimGame(p);
                    })
                )
                .withPermission("practicehub.command.bowaim.start.other")
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    if (BowAim.world == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bowAimName)));
                        return;
                    }

                    PracticeHubPlayer player = PracticeHubPlayer.linkedPlayers.get(p);
                    if (player == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.notInGame.replace("%gamemode%", Messages.bowAimName)));
                        return;
                    }

                    p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.endGame.replace("%gamemode%", Messages.bowAimName)));
                    BowAim.endBowAimGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (BowAim.world == null) {
                            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.bowAimName)));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.endGame.replace("%gamemode%", Messages.bowAimName)));
                        BowAim.endBowAimGame(p);
                    })
                )
                .withPermission("practicehub.command.bowaim.end.other")
            )
            .withPermission("commandutils.command.bowaim")
            .register("practicehub");
    }

}
