package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.LilyPadBridge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.entity.Player;

public class LilyPadBridgeCommand {

    public static void register() {
        new CommandTree("lilypadbridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    if (LilyPadBridge.copyWorld == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.lilyPadBridgeName)));
                        return;
                    }
                    LilyPadBridge.startGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (LilyPadBridge.copyWorld == null) {
                            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.lilyPadBridgeName)));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        LilyPadBridge.startGame(p);
                    })
                )
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    if (LilyPadBridge.copyWorld == null) {
                        p.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.lilyPadBridgeName)));
                        return;
                    }
                    LilyPadBridge.endGame(p);
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (LilyPadBridge.copyWorld == null) {
                            sender.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.gamemodeDisabledMessage.replace("%gamemode%", Messages.lilyPadBridgeName)));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        LilyPadBridge.endGame(p);
                    })
                )
            )
            .withPermission("practicehub.command.lilypadbridge")
            .register("practicehub");
    }

}
