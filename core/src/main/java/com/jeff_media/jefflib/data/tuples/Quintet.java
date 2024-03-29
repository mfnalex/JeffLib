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

import java.util.Objects;
import org.jetbrains.annotations.Nullable;

/**
 * Tuple of five values. If you ever need more, <b>just create your own data class</b> (sigh)
 *
 * @param <A> Type of first element
 * @param <B> Type of second element
 * @param <C> Type of third element
 * @param <D> Type of fourth element
 * @param <E> Type of fifth element
 */
public class Quintet<A, B, C, D, E> extends Quartet<A, B, C, D> {

    @Nullable
    E fifth;

    public Quintet(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth, @Nullable final E fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> of(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth, @Nullable final E fifth) {
        return new Quintet<>(first, second, third, fourth, fifth);
    }

    @Nullable
    public E getFifth() {
        return fifth;
    }

    public void setFifth(@Nullable E fifth) {
        this.fifth = fifth;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fifth);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        final Quintet<?, ?, ?, ?, ?> quintet = (Quintet<?, ?, ?, ?, ?>) o;
        return Objects.equals(fifth, quintet.fifth);
    }

    @Override
    public String toString() {
        return "Quintet{" + "first=" + first + ", second=" + second + ", third=" + third + ", fourth=" + fourth + ", fifth=" + fifth + '}';
    }
}
