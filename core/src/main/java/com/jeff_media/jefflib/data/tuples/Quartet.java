/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.data.tuples;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Tuple of four values
 *
 * @param <A> Type of first element
 * @param <B> Type of second element
 * @param <C> Type of third element
 * @param <D> Type of fourth element
 */
public class Quartet<A, B, C, D> extends Triplet<A, B, C> {

    @Nullable
    D fourth;

    public Quartet(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    public static <A, B, C, D> Quartet<A, B, C, D> of(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth) {
        return new Quartet<>(first, second, third, fourth);
    }

    @Nullable
    public D getFourth() {
        return fourth;
    }

    public void setFourth(@Nullable D fourth) {
        this.fourth = fourth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fourth);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Quartet<?, ?, ?, ?> quartet = (Quartet<?, ?, ?, ?>) o;
        return Objects.equals(fourth, quartet.fourth);
    }

    @Override
    public String toString() {
        return "Quartet{first=" + first + ", second=" + second + ", third=" + third + ", fourth=" + fourth + '}';
    }
}
