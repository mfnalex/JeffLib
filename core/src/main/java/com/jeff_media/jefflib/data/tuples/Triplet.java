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
 * Tuple of three values
 *
 * @param <A> Type of first element
 * @param <B> Type of second element
 * @param <C> Type of third element
 */
public class Triplet<A, B, C> extends Pair<A, B> {

    @Nullable
    C third;

    public Triplet(@Nullable final A first, @Nullable final B second, @Nullable final C third) {
        super(first, second);
        this.third = third;
    }

    public static <A, B, C> Triplet<A, B, C> of(@Nullable final A first, @Nullable final B second, @Nullable final C third) {
        return new Triplet<>(first, second, third);
    }

    @Nullable
    public C getThird() {
        return third;
    }

    public void setThird(@Nullable C third) {
        this.third = third;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), third);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Triplet<?, ?, ?> triplet = (Triplet<?, ?, ?>) o;
        return Objects.equals(third, triplet.third);
    }

    @Override
    public String toString() {
        return "Triplet{" + "first=" + first + ", second=" + second + ", third=" + third + '}';
    }
}
