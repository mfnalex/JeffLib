package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

@UtilityClass
public class NumberUtils {

    public boolean isInteger(String text) {
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Nullable
    public Integer parseInteger(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
