package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

/**
 * Class related methods that do not have something to do with Reflection (see {@link ReflUtils} for that)
 */
@UtilityClass
public final class ClassUtils {

    public static boolean exists(@NotNull final String name) {
        try {
            Class.forName(name);
            return true;
        } catch (final ClassNotFoundException exception) {
            return false;
        }
    }
}
