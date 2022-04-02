package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class NumberUtils {

    /**
     * Checks if a given String is an Integer.
     */
    public boolean isInteger(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    /**
     * Returns the given String as Integer, or null if it isn't an Integer.
     */
    @Nullable
    public Integer parseInteger(final String text) {
        try {
            return Integer.parseInt(text);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
