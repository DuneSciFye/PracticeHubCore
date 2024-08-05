package me.dunescifye.practicehubcore.utils;

import java.time.Duration;

public class Utils {

    public static String getFormattedTime(Duration duration) {
        if (duration.compareTo(Duration.ofHours(1)) > 0) {
            return "You lasted "
                + duration.toHoursPart() + " hours, "
                + duration.toMinutesPart() + " minutes, & "
                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        } else if (duration.compareTo(Duration.ofMinutes(1)) > 0) {
            return "You lasted "
                + duration.toMinutesPart() + " minutes & "
                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        } else {
            return "You lasted "
                + duration.toSecondsPart() + "." + duration.toMillisPart() + " seconds.";
        }
    }

}
