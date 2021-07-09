package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

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

    public static String formatNanoseconds(long nanoSeconds) {
        return String.format(Locale.ROOT,"%.4f ms",nanoSecondsToMilliSecondsDouble(nanoSeconds));
    }

    /**
     * Starts to record the time, using a String identifier, to be used in conjunction with {@link #endTimings(String)}
     * @param identifier Name of the task to be measured
     */
    public static void startTimings(String identifier) {
        measurements.put(identifier, System.nanoTime());
    }

    public static long endTimings(String identifier, @Nullable Plugin plugin, boolean sendMessage) {
        long now = System.nanoTime();
        Long then = measurements.get(identifier);
        if (then == null) {
            throw new IllegalArgumentException("No timings with identifier \"" + identifier + "\" running");
        }
        long nanoseconds = now - then;
        double milliseconds = nanoSecondsToMilliSecondsDouble(nanoseconds);
        if(sendMessage) {
            Logger logger = plugin == null ? Bukkit.getLogger() : plugin.getLogger();
            logger.info(String.format(Locale.ROOT,"Task \"%s\" finished in %.4f ms", identifier, milliseconds));
        }
        return nanoseconds;
    }

    /**
     * Ends the time measurement, prints out the duration in either nanoseconds or milliseconds
     * @param identifier Name of the task to be measured
     * @return duration the task took in nanoseconds
     */
    public static long endTimings(String identifier, boolean sendMessage) {
        return endTimings(identifier,null, sendMessage);
    }

    public static long endTimings(String identifier) {
        return endTimings(identifier, true);
    }

}
