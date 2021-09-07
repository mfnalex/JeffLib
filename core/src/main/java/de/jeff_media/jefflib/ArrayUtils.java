package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@UtilityClass
public class ArrayUtils {

    public static <T> T[] removeAtIndex(final T[] arr, final int index) {
        if(arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.remove(index);
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(),0));
    }

    public static <T> T[] addAfter(final T[] arr, final T object) {
        final List<T> list = new ArrayList<>(Arrays.asList(arr));
        list.add(object);
        return list.toArray((T[]) Array.newInstance(arr.getClass().getComponentType(), 0));
    }
}
