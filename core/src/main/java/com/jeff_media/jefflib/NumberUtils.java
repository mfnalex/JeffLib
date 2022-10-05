/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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

    public static boolean isEqual(final double number1, final double number2) {
        return isZero(number1 - number2);
    }

    /**
     * Checks if the given number is close to zero
     */
    public static boolean isZero(final double number) {
        return Math.abs(number) < EPSILON_D;
    }

    public static boolean isEqual(final float number1, final float number2) {
        return isZero(number1 - number2);
    }

    /**
     * Checks if the given number is close to zero
     */
    public static boolean isZero(final float number) {
        return Math.abs(number) < EPSILON_F;
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
     * Checks if the given number is close to zero
     */
    public static boolean isZero(@Nullable final Number number) {
        return number == null || isZero(number.doubleValue());
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

    public static boolean isInbetweenInclusive(final int number, final int min, final int max) {
        return number >= min && number <= max;
    }
}
