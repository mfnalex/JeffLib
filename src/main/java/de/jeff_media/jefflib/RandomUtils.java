package de.jeff_media.jefflib;

/**
 * Random number related methods
 */
public class RandomUtils {

    /**
     * Returns true with a specific chance, otherwise false, with a range between 0 (always false) and 1 (always true)
     *
     * @param chance chance to return true (0-1)
     * @return true when the chance succeeds, otherwise false
     */
    public static boolean chance(double chance) {
        return getDouble(0, 1) <= chance;
    }

    /**
     * Returns true with a specific chance, otherwise false, with a range between 0 (always false) and 100 (always true)
     *
     * @param chance chance to return true (0-100)
     * @return true when the chance succeeds, otherwise false
     */
    public static boolean chance100(double chance) {
        return getDouble(0, 100) <= chance;
    }

    /**
     * Returns a double between {@param min} (inclusive) and {@param max} (exclusive)
     *
     * @param min min
     * @param max max (exclusive)
     * @return double between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static double getDouble(double min, double max) {
        return JeffLib.getThreadLocalRandom().nextDouble(min, max);
    }

    /**
     * Returns an int between {@param min} (inclusive) and {@param max} (exclusive)
     *
     * @param min min
     * @param max max (exclusive)
     * @return int between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static int getInt(int min, int max) {
        return JeffLib.getRandom().nextInt(max - min) + min;
    }
}
