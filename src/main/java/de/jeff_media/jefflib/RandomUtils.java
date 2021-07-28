package de.jeff_media.jefflib;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Provides methods to generate numbers in a certain bound
 */
public class RandomUtils {

    /**
     * Returns a double between {@param min} (inclusive) and {@param max} (exclusive)
     * @param min min
     * @param max max (exclusive)
     * @return double between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static double getDouble(double min, double max) {
        return JeffLib.getThreadLocalRandom().nextDouble(min,max);
    }

    /**
     * Returns an int between {@param min} (inclusive) and {@param max} (exclusive)
     * @param min min
     * @param max max (exclusive)
     * @return int between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static int getInt(int min, int max) {
        return JeffLib.getRandom().nextInt(max-min) + min;
    }
}
