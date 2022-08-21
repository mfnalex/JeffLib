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

/**
 * Converts seconds, minutes or hours to their correseponding amount of ticks
 */
@UtilityClass
public class Ticks {

    /**
     * Converts hours to ticks
     *
     * @param hours Duration
     * @return Duration in ticks
     */
    public static long fromHours(final double hours) {
        return fromMinutes(hours * 60);
    }

    /**
     * Converts minutes to ticks
     *
     * @param minutes Duration
     * @return Duration in ticks
     */
    public static long fromMinutes(final double minutes) {
        return fromSeconds(minutes * 60);
    }

    /**
     * Converts seconds to ticks
     *
     * @param seconds Duration
     * @return Duration in ticks
     */
    public static long fromSeconds(final double seconds) {
        return (long) (seconds * 20);
    }
}
