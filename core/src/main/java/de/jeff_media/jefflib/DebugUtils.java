package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import java.util.Map;

@UtilityClass
public class DebugUtils {

    /**
     * Prints a Map's content to console
     * @param map Map to print
     */
    public static void print(final Map<?,?> map) {
        for(final Map.Entry<?,?> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" -> " + entry.getValue());
        }
    }
}
