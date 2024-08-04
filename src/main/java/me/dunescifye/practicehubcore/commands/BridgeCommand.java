package me.dunescifye.practicehubcore.commands;

import dev.jorel.commandapi.CommandTree;
import dev.jorel.commandapi.arguments.LiteralArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.files.BridgeConfig;
import me.dunescifye.practicehubcore.files.Config;
import me.dunescifye.practicehubcore.gamemodes.Bridge;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

public class BridgeCommand {

    public static void register() {
        new CommandTree("bridge")
            .then(new LiteralArgument("start")
                .executesPlayer((p, args) -> {
                    //Gamemode disabled
                    if (BridgeConfig.bridgeCopyWorld == null) {
                        p.sendMessage(Component.text("Bridge is disabled!"));
                        return;
                    }
                    Bridge.startBridgeGame(p);
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        if (BridgeConfig.bridgeCopyWorld == null) {
                            sender.sendMessage(Component.text("Bridge is disabled!"));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        Bridge.startBridgeGame(p);
                    })
                    .withPermission("practicehub.command.bridge.other")
                )
            )
            .then(new LiteralArgument("end")
                .executesPlayer((p, args) -> {
                    String currentGamemode = PracticeHubCore.gamemode.get(p);
                    if (currentGamemode == null) {
                        p.sendMessage(Component.text("You are not in any game!"));
                        return;
                    }
                    p.getInventory().clear();
                    p.getInventory().setContents(PracticeHubCore.inventories.remove(p));
                    PracticeHubCore.gamemode.remove(p);
                    Bridge.tasks.remove(p).cancel();
                    p.sendMessage(Component.text("Ended game!"));
                    p.teleport(Config.spawn);
                    PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
                })
                .then(new PlayerArgument("Player")
                    .executes((sender, args) -> {
                        //Bridge is disabled
                        if (BridgeConfig.bridgeCopyWorld == null) {
                            sender.sendMessage(Component.text("Bridge is disabled!"));
                            return;
                        }
                        Player p = args.getUnchecked("Player");
                        assert p != null;
                        String currentGamemode = PracticeHubCore.gamemode.get(p);
                        if (currentGamemode == null) {
                            sender.sendMessage(Component.text(p.getName() + " is not in any game!"));
                            return;
                        }
                        p.getInventory().clear();
                        p.getInventory().setContents(PracticeHubCore.inventories.remove(p));
                        PracticeHubCore.gamemode.remove(p);
                        Bridge.tasks.remove(p).cancel();
                        p.sendMessage(Component.text("Ended game!"));
                        p.teleport(Config.spawn);
                        PracticeHubCore.worldManager.deleteWorld("bridge" + p.getName());
                    })
                )
            )
            .withPermission("practicehub.command.bridge")
            .register("practicehub");
    }
}
