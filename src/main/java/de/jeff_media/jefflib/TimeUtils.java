package de.jeff_media.jefflib;

public class TimeUtils {

    private static final int milliSecondsPerTick = 50;

    public static long nanoSecondsToMilliSeconds(long nanoSeconds) {
        return nanoSeconds / 1000000;
    }

    public static double milliSecondsToTickPercentage(long milliSeconds) {
        return (double) (milliSeconds / milliSecondsPerTick * 100);
    }

}
