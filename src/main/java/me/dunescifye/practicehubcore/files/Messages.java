package me.dunescifye.practicehubcore.files;

import me.dunescifye.practicehubcore.PracticeHubCore;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class Messages {
    public static String gamemodeDisabledMessage, bowAimName, bowBoostName, bridgeName, endGame, notInGame, notInGameOther, onlyPlayerCommand,
        onlyConsoleCommand, reloadedConfigMessage, selfPingMessage, otherPingMessage, teleportMessage, shieldPVPName, challengingMessage,
        challengedMessage, noChallengeMessage, lilyPadBridgeName, fallClutchName, parkourName, portalBuildName, invalidWorldMessage, invalidConfigType
        ;

    public static void setup() {
        PracticeHubCore plugin = PracticeHubCore.getPlugin();

        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) {
            plugin.saveResource("messages.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(messagesFile);

        selfPingMessage = config.getString("Commands.Ping.OnSelf");
        otherPingMessage = config.getString("Commands.Ping.OnOther");
        teleportMessage = config.getString("Commands.SpawnTeleport");
        reloadedConfigMessage = config.getString("Commands.ReloadedConfig");

        gamemodeDisabledMessage = config.getString("Gamemodes.Global.Disabled");

        //Error messages
        invalidWorldMessage = config.getString("ErrorMessages.InvalidWorld");
        invalidConfigType = config.getString("ErrorMessages.InvalidConfigType");

        bowAimName = config.getString("Gamemodes.BowAim.Name");
        bowBoostName = config.getString("Gamemodes.BowBoost.Name");
        bridgeName = config.getString("Gamemodes.Bridge.Name");
        shieldPVPName = config.getString("Gamemodes.ShieldPVP.Name");
        lilyPadBridgeName = config.getString("Gamemodes.LilyPadBridge.Name");
        fallClutchName = config.getString("Gamemodes.FallClutch.Name");
        parkourName = config.getString("Gamemodes.Parkour.Name");
        portalBuildName = config.getString("Gamemodes.PortalBuild.Name");

        endGame = config.getString("Commands.EndGame.Ending");
        notInGame = config.getString("Commands.EndGame.NotInGame");
        notInGameOther = config.getString("Commands.EndGame.NotInGameOther");

        onlyPlayerCommand = config.getString("Commands.OnlyPlayerCommand");
        onlyConsoleCommand = config.getString("Commands.OnlyConsoleCommand");

        challengingMessage = config.getString("Gamemodes.ShieldPVP.Challenging");
        challengedMessage = config.getString("Gamemodes.ShieldPVP.Challenged");
        noChallengeMessage = config.getString("Gamemodes.ShieldPVP.NoChallenge");


    }

}
