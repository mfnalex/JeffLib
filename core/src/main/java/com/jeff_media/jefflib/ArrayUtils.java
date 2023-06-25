/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import java.lang.reflect.Array;
import java.util.Arrays;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

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
        return (T[]) Arrays.stream(array).flatMap(Arrays::stream).toArray();
    }

    /**
     * Returns a new array of the given class type with length 0
     *
     * @param componentType Class of the array's component type
     * @param <T>           Array's component type
     * @return Array of the given class type with length 0
     */
    public static <T> T[] createArray(@NotNull final Class<T> componentType) {
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
    public static <T> T[] createArray(@NotNull final Class<T> componentType, final int length) {
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
        T[] newArr = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length - 1);
        System.arraycopy(arr, 0, newArr, 0, index);
        System.arraycopy(arr, index + 1, newArr, index, arr.length - index - 1);
        return newArr;
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
        T[] newArr = Arrays.copyOf(arr, arr.length + 1);
        newArr[arr.length] = object;
        return newArr;
    }
}
