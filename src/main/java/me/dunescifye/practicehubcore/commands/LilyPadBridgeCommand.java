package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.LilyPadBridge;
import net.kyori.adventure.text.Component;

public class LilyPadBridgeCommand {

    public static void setup() {
        new CommandTree("lilypadbridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    if (LilyPadBridge.copyWorld == null) {
                        p.sendMessage(Component.text("Lily Pad Gamemode is Disabled!"));
                        return;
                    }
                    LilyPadBridge.startGame(p);
                })
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    if (LilyPadBridge.copyWorld == null) {
                        p.sendMessage(Component.text("Lily Pad Gamemode is Disabled!"));
                        return;
                    }
                    LilyPadBridge.endGame(p);
                })
            )
            .withPermission("practicehub.command.lilypadbridge")
            .register("practicehub");
    }

}
