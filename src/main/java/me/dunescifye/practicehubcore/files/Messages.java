package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.commands.MiscCommands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Messages {
    public static String gamemodeDisabledMessage, bowAimName, bowBoostName, bridgeName, endGame, notInGame, notInGameOther, onlyPlayerCommand,
        onlyConsoleCommand, reloadedConfigMessage, selfPingMessage, otherPingMessage, teleportMessage;

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "messages.yml"), plugin.getResource("messages.yml"));

            selfPingMessage = config.getString("Commands.Ping.OnSelf");
            otherPingMessage = config.getString("Commands.Ping.OnOther");
            teleportMessage = config.getString("Commands.SpawnTeleport");
            reloadedConfigMessage = config.getString("Commands.ReloadedConfig");

            gamemodeDisabledMessage = config.getString("Gamemodes.Global.Disabled");

            bowAimName = config.getString("Gamemodes.BowAim.Name");
            bowBoostName = config.getString("Gamemodes.BowBoost.Name");
            bridgeName = config.getString("Gamemodes.Bridge.Name");

            endGame = config.getString("Commands.EndGame.Ending");
            notInGame = config.getString("Commands.EndGame.NotInGame");
            notInGameOther = config.getString("Commands.EndGame.NotInGameOther");

            onlyPlayerCommand = config.getString("Commands.OnlyPlayerCommand");
            onlyConsoleCommand = config.getString("Commands.OnlyConsoleCommand");


        } catch (
            IOException e) {
            logger.severe("messages.yml file failed to load!");
            throw new RuntimeException(e);
        }

    }

}
