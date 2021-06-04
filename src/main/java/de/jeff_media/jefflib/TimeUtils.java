package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

import java.util.HashMap;

public class TimeUtils {

    private static final int milliSecondsPerTick = 50;
    private static final HashMap<String, Long> measurements = new HashMap<>();

    public static long nanoSecondsToMilliSeconds(long nanoSeconds) {
        return nanoSeconds / 1000000;
    }

    public static double nanoSecondsToMilliSecondsDouble(long nanoSeconds) {
        return ((double) nanoSeconds) / (double) 1000000;
    }

    public static double milliSecondsToTickPercentage(long milliSeconds) {
        return (double) (milliSeconds / milliSecondsPerTick * 100);
    }

    public static void startTimings(String identifier) {
        measurements.put(identifier, System.nanoTime());
    }

    public static void endTimings(String identifier) {
        long now = System.nanoTime();
        Long then = measurements.get(identifier);
        if(then == null) {
            throw new IllegalArgumentException("No timings with identifier \""+identifier+"\" running");
        }
        long nanoseconds = now - then;
        double milliseconds = nanoSecondsToMilliSecondsDouble(nanoseconds);
        Bukkit.getLogger().info(String.format("Task \"%s\" finished in %.4f ms", identifier, milliseconds));
    }

}
