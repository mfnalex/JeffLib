package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import javax.annotation.Nullable;

/**
 * Number parsing related methods
 */
@UtilityClass
public class NumberUtils {


    public static final double EPSILON_D = 1e-6;

    public static final float EPSILON_F = 1e-6f;

    /**
     * Checks if the given number is close to zero
     */
    public static boolean isZero(final double number) {
        return Math.abs(number) < EPSILON_D;
    }

    /**
     * Checks if the given number is close to zero
     */
    public static boolean isZero(final float number) {
        return Math.abs(number) < EPSILON_F;
    }

    /**
     * Checks if the given number is close to zero
     */
    public static boolean isZero(@Nullable final Number number) {
        return number == null || isZero(number.doubleValue());
    }

    /**
     * Checks if the given number is close to or higher than zero
     */
    public static boolean isZeroOrPositive(final double number) {
        return isZero(number) || number >= EPSILON_D;
    }

    /**
     * Checks if the given number is close to or higher than zero
     */
    public static boolean isZeroOrPositive(final float number) {
        return isZero(number) || number >= EPSILON_F;
    }

    /**
     * Checks if the given number is close to or higher than zero
     */
    public static boolean isZeroOrPositive(@Nullable final Number number) {
        return isZero(number) || number.doubleValue() >= EPSILON_D;
    }

    /**
     * Checks if the given number is close to or lower than zero
     */
    public static boolean isZeroOrNegative(final double number) {
        return isZero(number) || number <= EPSILON_D;
    }

    /**
     * Checks if the given number is close to or lower than zero
     */
    public static boolean isZeroOrNegative(final float number) {
        return isZero(number) || number <= EPSILON_F;
    }

    /**
     * Checks if the given number is close to or lower than zero
     */
    public static boolean isZeroOrNegative(@Nullable final Number number) {
        return isZero(number) || number.doubleValue() <= EPSILON_D;
    }

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
