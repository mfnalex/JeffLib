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

package com.jeff_media.jefflib.data.average;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;

/**
 * Represents an average double value consisting of a given number of previous values
 */
public final class AverageDouble {

    @Getter private final int maxSamples;
    @Getter private final double[] data;
    @Getter private int index = 0;
    @Getter private int currentSamples = 0;

    /**
     * Creates a new instance that stores up to the given number of samples
     */
    public AverageDouble(final int maxSamples) {
        this.maxSamples = maxSamples;
        this.data = new double[maxSamples];
    }

    @Override
    public String toString() {
        return "AverageDouble{" + "maxSamples=" + maxSamples + ", data=" + Arrays.toString(data) + ", index=" + index + ", currentSamples=" + currentSamples + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final AverageDouble that = (AverageDouble) o;
        return maxSamples == that.maxSamples && index == that.index && currentSamples == that.currentSamples && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(maxSamples, index, currentSamples);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    /**
     * Adds a new value to the average
     */
    public void add(final double value) {
        data[index] = value;
        index = (index + 1) % maxSamples;
        if(currentSamples != maxSamples) {
            currentSamples++;
        }
    }

    /**
     * Gets the average of the stored values
     */
    public double getAverage() {
        double sum = 0;
        for (int i = 0; i < currentSamples; i++) {
            sum += data[i];
        }
        return sum / currentSamples;
    }
}
