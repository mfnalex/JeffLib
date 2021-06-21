package de.jeff_media.jefflib;

import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Provides time related methods, like measuring time and converting nanoseconds to milliseconds, etc.
 */
public class TimeUtils {

    private static final int milliSecondsPerTick = 50;
    private static final HashMap<String, Long> measurements = new HashMap<>();

    /**
     * Converts nanoseconds to milliseconds
     * @param nanoSeconds duration in nanoseconds
     * @return duration in milliseconds
     */
    public static long nanoSecondsToMilliSeconds(long nanoSeconds) {
        return nanoSeconds / 1000000;
    }

    /**
     * Converts nanoseconds to milliseconds, returning a double value
     * @param nanoSeconds duration in nanoseconds
     * @return duration in milliseconds
     */
    public static double nanoSecondsToMilliSecondsDouble(long nanoSeconds) {
        return ((double) nanoSeconds) / (double) 1000000;
    }

    public static double milliSecondsToTickPercentage(long milliSeconds) {
        return (double) (milliSeconds / milliSecondsPerTick * 100);
    }

    /**
     * Starts to record the time, using a String identifier, to be used in conjunction with {@link #endTimings(String)}
     * @param identifier Name of the task to be measured
     */
    public static void startTimings(String identifier) {
        measurements.put(identifier, System.nanoTime());
    }

    /**
     * Ends the time measurement, prints out the duration in either nanoseconds or milliseconds
     * @param identifier Name of the task to be measured
     * @return duration the task took in nanoseconds
     */
    public static long endTimings(String identifier) {
        long now = System.nanoTime();
        Long then = measurements.get(identifier);
        if (then == null) {
            throw new IllegalArgumentException("No timings with identifier \"" + identifier + "\" running");
        }
        long nanoseconds = now - then;
        double milliseconds = nanoSecondsToMilliSecondsDouble(nanoseconds);
        Bukkit.getLogger().info(String.format("Task \"%s\" finished in %.4f ms", identifier, milliseconds));
        return nanoseconds;
    }

}
