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

package com.jeff_media.jefflib.data.tuples;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Tuple of two values
 *
 * @param <A> Type of first element
 * @param <B> Type of second element
 */
public class Pair<A, B> {

    @Nullable
    A first;
    @Nullable
    B second;

    public Pair(@Nullable final A first, @Nullable final B second) {
        this.first = first;
        this.second = second;
    }

    public static <A, B> Pair<A, B> of(@Nullable final A first, @Nullable final B second) {
        return new Pair<>(first, second);
    }

    @Nullable
    public A getFirst() {
        return first;
    }

    public void setFirst(@Nullable A first) {
        this.first = first;
    }

    @Nullable
    public B getSecond() {
        return second;
    }

    public void setSecond(@Nullable B second) {
        this.second = second;
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + first + ", second=" + second + '}';
    }

}
