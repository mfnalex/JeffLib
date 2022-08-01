package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

/**
 * Converts seconds, minutes or hours to their correseponding amount of ticks
 */
@UtilityClass
public final class Ticks {

    /**
     * Converts hours to ticks
     *
     * @param hours Duration
     * @return Duration in ticks
     */
    public static long fromHours(final double hours) {
        return fromMinutes(hours * 60);
    }

    /**
     * Converts minutes to ticks
     *
     * @param minutes Duration
     * @return Duration in ticks
     */
    public static long fromMinutes(final double minutes) {
        return fromSeconds(minutes * 60);
    }

    /**
     * Converts seconds to ticks
     *
     * @param seconds Duration
     * @return Duration in ticks
     */
    public static long fromSeconds(final double seconds) {
        return (long) (seconds * 20);
    }
}
