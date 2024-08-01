package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class BridgeCommand {

    public static HashMap<Player, String> gamemode;

    public static void register() {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((player, args) -> {
                    player.sendMessage(Component.text("Starting!"));
                    player.getInventory().clear();
                    player.getInventory().setItemInMainHand(new ItemStack(Material.OAK_LOG, 64));
                    gamemode.put(player, "bridge");
                })
            )
            .withPermission("practicehub.command.bridge")
            .register("practicehub");
    }

}
