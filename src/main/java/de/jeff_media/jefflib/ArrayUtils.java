package de.jeff_media.jefflib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayUtils {

    public static <T> T[] removeNthElement(T[] array, int n) {
        List<T> list = new ArrayList<>(Arrays.asList(array));
        list.remove(n);
        return (T[]) list.toArray();
    }
}
