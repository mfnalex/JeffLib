package com.jeff_media.jefflib.data;

import java.util.Arrays;

/**
 * Represents the server's last TPS
 */
public class TPS {

    private final double last1Minute;
    private final double last5Minute;
    private final double last15Minute;

    public TPS(final double[] tps) {
        if (tps.length != 3) {
            throw new IllegalArgumentException("TPS array doesn't contain 3 values but " + tps.length + ": " + Arrays.toString(tps));
        }

        this.last1Minute = tps[0];
        this.last5Minute = tps[1];
        this.last15Minute = tps[2];
    }

    /**
     * Gets the average TPS during the last minute
     */
    public double getLast1Minute() {
        return last1Minute;
    }

    /**
     * Gets the average TPS during the last 5 minutes
     */
    public double getLast5Minute() {
        return last5Minute;
    }

    /**
     * Gets the average TPS during the last 15 minutes
     */
    public double getLast15Minute() {
        return last15Minute;
    }
}
