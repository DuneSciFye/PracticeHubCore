package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.PlayerArgument;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class MiscCommands {

    public static void register() {
        new CommandTree("ping")
            .executesPlayer((p, args) -> {
                p.sendMessage(Component.text("Your ping is " + p.getPing() + "ms"));
            })
            .then(new PlayerArgument("Player")
                .executes((sender, args) -> {
                    Player p = args.getUnchecked("Player");
                    assert p != null;
                    sender.sendMessage(p.getName() + "'s ping is " + p.getPing());
                })
                .withPermission("practicehub.command.ping.other")
            )
            .withPermission("practicehub.command.ping")
            .register("practicehub");
    }

}
