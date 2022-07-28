package com.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    @Nullable
    D fourth;

    public Quartet(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    public static <A, B, C, D> Quartet<A, B, C, D> of(@Nullable final A first, @Nullable final B second, @Nullable final C third, @Nullable final D fourth) {
        return new Quartet<>(first, second, third, fourth);
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), fourth);
    }

    @Override
    public String toString() {
        return "Quartet{first=" + first + ", second=" + second + ", third=" + third + ", fourth=" + fourth + '}';
    }
}
