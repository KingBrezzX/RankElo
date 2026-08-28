package net.rankelo.plugin.utils;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static String formatSeconds(long seconds) {
        if (seconds < 0) {
            seconds = 0;
        }

        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        if (minutes > 0) {
            return minutes + "m " + remainingSeconds + "s";
        }

        return remainingSeconds + "s";
    }

    public static long millisecondsToSeconds(long milliseconds) {
        return Math.max(0L, milliseconds / 1000L);
    }
}
