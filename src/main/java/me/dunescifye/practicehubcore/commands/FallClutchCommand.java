package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.gamemodes.FallClutch;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FallClutchCommand {

    public static void register() {
        new CommandTree("fallclutch")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random"))
                .then(new LiteralArgument("waterbucket")
                    .executesPlayer((p, args) -> {
                        FallClutch.startGame(p, new ItemStack(Material.WATER_BUCKET));
                    })
                )
                .then(new LiteralArgument("boat")
                    .executesPlayer((p, args) -> {
                        FallClutch.startGame(p, new ItemStack(Material.OAK_BOAT));
                    })
                )
                .then(new LiteralArgument("scaffolding")
                    .executesPlayer((p, args) -> {
                        FallClutch.startGame(p, new ItemStack(Material.SCAFFOLDING));
                    })
                )
                .then(new LiteralArgument("berry")
                    .executesPlayer((p, args) -> {
                        FallClutch.startGame(p, new ItemStack(Material.SWEET_BERRIES));
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    FallClutch.endGame(p);
                })
            )
            .withPermission("practicehub.command.fallclutch")
            .register("practicehub");
    }

}
