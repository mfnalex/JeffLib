package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class NumberUtils {

    public boolean isInteger(final String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (final NumberFormatException e) {
            return false;
        }
    }

    @Nullable
    public Integer parseInteger(final String text) {
        try {
            return Integer.parseInt(text);
        } catch (final NumberFormatException e) {
            return null;
        }
    }
}
