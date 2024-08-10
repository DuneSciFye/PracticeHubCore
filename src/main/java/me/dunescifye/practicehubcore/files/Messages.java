package me.dunescifye.practicehubcore.files;

import dev.dejvokep.boostedyaml.YamlDocument;
import me.dunescifye.practicehubcore.PracticeHubCore;
import me.dunescifye.practicehubcore.commands.MiscCommands;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public class Messages {
    public static String gamemodeDisabledMessage;
    public static String bowAimName;
    public static String bowBoostName;
    public static String endGame;
    public static String notInGame;

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();
        Logger logger = plugin.getLogger();

        try {
            YamlDocument config = YamlDocument.create(new File(plugin.getDataFolder(), "messages.yml"), plugin.getResource("messages.yml"));

            MiscCommands.setSelfPingMessage(config.getString("Commands.Ping.OnSelf"));
            MiscCommands.setOtherPingMessage(config.getString("Commands.Ping.OnOther"));

            gamemodeDisabledMessage = config.getString("Gamemodes.Global.Disabled");
            bowAimName = config.getString("Gamemodes.BowAim.Name");
            bowBoostName = config.getString("Gamemodes.BowBoost.Name");
            endGame = config.getString("Commands.EndGame.Ending");
            notInGame = config.getString("Commands.EndGame.NotInGame");

        } catch (
            IOException e) {
            logger.severe("messages.yml file failed to load!");
            throw new RuntimeException(e);
        }

    }

}
