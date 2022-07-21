package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Logger;

/**
 * Time related methods, like measuring time and converting nanoseconds to milliseconds, etc.
 */
@UtilityClass
public class TimeUtils {

    public static final int MILLISECONDS_PER_TICK = 50;
    private static final HashMap<String, Long> measurements = new HashMap<>();

    /**
     * Converts nanoseconds to milliseconds
     *
     * @param nanoSeconds duration in nanoseconds
     * @return duration in milliseconds
     */
    public static long nanoSecondsToMilliSeconds(final long nanoSeconds) {
        return nanoSeconds / 1000000;
    }

    /**
     * Converts nanoseconds to milliseconds, returning a double value
     *
     * @param nanoSeconds duration in nanoseconds
     * @return duration in milliseconds
     */
    public static double nanoSecondsToMilliSecondsDouble(final long nanoSeconds) {
        return ((double) nanoSeconds) / (double) 1000000;
    }

    /**
     * Converts an amount of milliseconds to tick percentage. E.g. 50 will return 100 and 25 will return 50
     *
     * @param milliSeconds milliseconds
     * @return percentage of tick
     */
    public static double milliSecondsToTickPercentage(final long milliSeconds) {
        return (double) (milliSeconds / MILLISECONDS_PER_TICK * 100);
    }

    /**
     * Formats nanoseconds to a human readable format in milliseconds with 4 decimal places
     *
     * @param nanoSeconds Duration in nanoseconds
     */
    public static String formatNanoseconds(final long nanoSeconds) {
        return String.format(Locale.ROOT, "%.4f ms", nanoSecondsToMilliSecondsDouble(nanoSeconds));
    }

    /**
     * Starts to record the time, using a String identifier, to be used in conjunction with {@link #endTimings(String)}
     *
     * @param identifier Name of the task to be measured
     */
    public static void startTimings(final String identifier) {
        measurements.put(identifier, System.nanoTime());
    }

    /**
     * Ends the time measurement and optionally prints out the duration in human readable milliseconds
     *
     * @param identifier  Name of the mask to be measured
     * @param plugin      Plugin of which Logger to use
     * @param sendMessage Whether to send a message
     * @return Duration in nanoseconds
     */
    public static long endTimings(final String identifier, @Nullable final Plugin plugin, final boolean sendMessage) {
        final long now = System.nanoTime();
        final Long then = measurements.get(identifier);
        if (then == null) {
            throw new IllegalArgumentException("No timings with identifier \"" + identifier + "\" running");
        }
        final long nanoseconds = now - then;
        final double milliseconds = nanoSecondsToMilliSecondsDouble(nanoseconds);
        if (sendMessage) {
            final Logger logger = plugin == null ? Bukkit.getLogger() : plugin.getLogger();
            logger.info(String.format(Locale.ROOT, "Task \"%s\" finished in %.4f ms", identifier, milliseconds));
        }
        return nanoseconds;
    }

    /**
     * Ends the time measurement, and optionally prints out the duration in human readable milliseconds
     *
     * @param identifier Name of the task to be measured
     * @return duration the task took in nanoseconds
     */
    public static long endTimings(final String identifier, final boolean sendMessage) {
        return endTimings(identifier, null, sendMessage);
    }

    /**
     * Ends the time measurement and prints out the duration in human readable milliseconds
     *
     * @param identifier Name of the task to be measured
     * @return duration the task took in nanoseconds
     */
    public static long endTimings(final String identifier) {
        return endTimings(identifier, true);
    }

}
