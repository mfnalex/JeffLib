/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

/**
 * Random number related methods
 */
@UtilityClass
public class RandomUtils {

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

    /**
     * The same as {@link EnumUtils#getRandomElement(Class)}
     *
     * @see EnumUtils#getRandomElement(Class)
     */
    public static <E extends Enum<E>> E getRandomEnumElement(final Class<E> enumClazz) {
        return EnumUtils.getRandomElement(enumClazz);
    }
}
