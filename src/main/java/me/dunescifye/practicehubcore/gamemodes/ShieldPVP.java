package me.dunescifye.practicehubcore.gamemodes;

public class ShieldPVP {

    private static boolean enabled = true;

    public static boolean isEnabled() {
        return enabled;
    }

    public static void setEnabled(boolean enabled) {
        ShieldPVP.enabled = enabled;
    }
}
