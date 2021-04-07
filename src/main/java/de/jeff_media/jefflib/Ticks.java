package de.jeff_media.jefflib;

public class Ticks {
    public static long fromSeconds(final double seconds) {
        return (long) (seconds * 20);
    }

    public static long fromMinutes(final double minutes) {
        return fromSeconds(minutes * 60);
    }

    public static long fromHours(final double hours) {
        return fromMinutes(hours * 60);
    }
}
