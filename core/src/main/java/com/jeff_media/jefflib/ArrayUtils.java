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

import javax.annotation.Nonnull;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Array manipulation related methods
 */
@UtilityClass
public class ArrayUtils {

    /**
     * Combines the given arrays into a new array
     *
     * @param array Arrays to combine
     * @param <T>   Array's component type
     * @return Combined array
     */
    @SafeVarargs
    public static <T> T[] combine(final T[]... array) {
        int totalSize = 0;
        for (final T[] part : array) {
            totalSize += part.length;
        }
        //noinspection unchecked
        final T[] combined = (T[]) Array.newInstance(array.getClass().getComponentType().getComponentType(), totalSize);
        int index = 0;
        for (final T[] part : array) {
            System.arraycopy(part, 0, combined, index, part.length);
            index += part.length;
        }
        return combined;
    }

    /**
     * Returns a new array of the given class type with length 0
     *
     * @param componentType Class of the array's component type
     * @param <T>           Array's component type
     * @return Array of the given class type with length 0
     */
    public static <T> T[] createArray(@Nonnull final Class<T> componentType) {
        return createArray(componentType, 0);
    }

    /**
     * Returns a new array of the given class type with the given length
     *
     * @param componentType Class of the array's component type
     * @param <T>           Array's component type
     * @param length        Array's length
     * @return Array of the given class type with the given length
     */
    public static <T> T[] createArray(@Nonnull final Class<T> componentType, final int length) {
        //noinspection unchecked
        return (T[]) Array.newInstance(componentType, length);
    }

    /**
     * Removes an item from the array at a given location, returning the remaining array
     *
     * @param arr   Array to remove from
     * @param index Index at which to remove from
     * @param <T>   Array type
     * @return Array with the desired item removed
     */
    public static <T> T[] removeAtIndex(final T[] arr, final int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.remove(index);
        //noinspection unchecked
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), 0));
    }

    /**
     * Appends an item to the given array
     *
     * @param arr    Array to append to
     * @param object Object to append
     * @param <T>    Array type
     * @return Array with the desired item appended
     */
    public static <T> T[] addAfter(final T[] arr, final T object) {
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.add(object);
        //noinspection unchecked
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), 0));
    }
}
