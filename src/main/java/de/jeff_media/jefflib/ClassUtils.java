package de.jeff_media.jefflib;

import org.jetbrains.annotations.NotNull;

/**
 * Class related methods that do not have something to do with Reflection (see {@link ReflUtils} for that)
 */
public class ClassUtils {

    public static boolean exists(@NotNull final String name) {
        try {
            Class.forName(name);
            return true;
        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }
}
