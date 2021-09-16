package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

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
     * Removes an item from the array at a given location, returning the remaining array
     * @param arr Array to remove from
     * @param index Index at which to remove from
     * @param <T> Array type
     * @return Array with the desired item removed
     */
    public static <T> T[] removeAtIndex(final T[] arr, final int index) {
        if(arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.remove(index);
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(),0));
    }

    /**
     * Appends an item to the given array
     * @param arr Array to append to
     * @param object Object to append
     * @param <T> Array type
     * @return Array with the desired item appended
     */
    public static <T> T[] addAfter(final T[] arr, final T object) {
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.add(object);
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), 0));
    }
}
