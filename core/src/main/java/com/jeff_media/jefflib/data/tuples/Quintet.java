package com.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;
import java.util.Objects;

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

    @Getter
    @Setter
    @Nullable
    E fifth;

    public Quintet(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth, @Nullable final E fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
    }

    public static <A, B, C, D, E> Quintet<A, B, C, D, E> of(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth, @Nullable final E fifth) {
        return new Quintet<>(first, second, third, fourth, fifth);
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), fifth);
    }

    @Override
    public String toString() {
        return "Quintet{" + "first=" + first + ", second=" + second + ", third=" + third + ", fourth=" + fourth + ", fifth=" + fifth + '}';
    }
}
