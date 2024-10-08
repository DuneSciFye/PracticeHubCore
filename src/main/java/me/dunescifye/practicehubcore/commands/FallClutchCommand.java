package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import me.dunescifye.practicehubcore.files.Messages;
import me.dunescifye.practicehubcore.gamemodes.FallClutch;
import me.dunescifye.practicehubcore.gamemodes.LilyPadBridge;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class FallClutchCommand {

    public static void register() {
        new CommandTree("fallclutch")
            .then(new LiteralArgument("start")
                .then(new LiteralArgument("random")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.WATER_BUCKET),
                            new ItemStack(Material.OAK_BOAT),
                            new ItemStack(Material.SCAFFOLDING),
                            new ItemStack(Material.SWEET_BERRIES),
                            new ItemStack(Material.POWDER_SNOW_BUCKET));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("waterbucket")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.WATER_BUCKET));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("boat")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.OAK_BOAT));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("scaffolding")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.SCAFFOLDING));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("berry")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.SWEET_BERRIES));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
                .then(new LiteralArgument("powdersnow")
                    .executesPlayer((p, args) -> {
                        if (FallClutch.world == null) {
                            p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                            return;
                        }
                        FallClutch.startGame(p, new ItemStack(Material.POWDER_SNOW_BUCKET));
                    })
                    .executesConsole((console, args) -> {
                        console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                    })
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    if (FallClutch.world == null) {
                        p.sendMessage(Component.text("Fall Clutch Gamemode is Disabled!"));
                        return;
                    }
                    FallClutch.endGame(p);
                })
                .executesConsole((console, args) -> {
                    console.sendMessage(LegacyComponentSerializer.legacyAmpersand().deserialize(Messages.onlyPlayerCommand));
                })
            )
            .withPermission("practicehub.command.fallclutch")
            .register("practicehub");
    }

}
