package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

/**
 * Random number related methods
 */
@UtilityClass
public final class RandomUtils {

    /**
     * Returns true with a specific chance, otherwise false, with a range between 0 (always false) and 1 (always true)
     *
     * @param chance chance to return true (0-1)
     * @return true when the chance succeeds, otherwise false
     */
    public static boolean chance(final double chance) {
        return getDouble(0, 1) <= chance;
    }

    /**
     * Returns a double between {@param min} (inclusive) and {@param max} (exclusive)
     *
     * @param min min
     * @param max max (exclusive)
     * @return double between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static double getDouble(final double min, final double max) {
        return JeffLib.getThreadLocalRandom().nextDouble(min, max);
    }

    /**
     * Returns true with a specific chance, otherwise false, with a range between 0 (always false) and 100 (always true)
     *
     * @param chance chance to return true (0-100)
     * @return true when the chance succeeds, otherwise false
     */
    public static boolean chance100(final double chance) {
        return getDouble(0, 100) <= chance;
    }

    /**
     * Returns an int between {@param min} (inclusive) and {@param max} (exclusive)
     *
     * @param min min
     * @param max max (exclusive)
     * @return int between {@param min} (inclusive) and {@param max} (exclusive)
     */
    public static int getInt(final int min, final int max) {
        return JeffLib.getThreadLocalRandom().nextInt(min, max);
    }

    public static <E extends Enum<E>> E getRandomEnumElement(final Class<E> enumClazz) {
        return EnumUtils.getRandomElement(enumClazz);
    }
}
