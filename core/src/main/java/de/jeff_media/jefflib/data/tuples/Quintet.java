package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Tuple of five values. If you ever need more, <b>just create your own data class</b> (sigh)
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
    private E fifth;

    public Quintet(final @Nullable A first,
                   final @Nullable B second,
                   final @Nullable C third,
                   final @Nullable D fourth,
                   final @Nullable E fifth) {
        super(first, second, third, fourth);
        this.fifth = fifth;
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
}
