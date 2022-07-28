package com.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;

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

    @Getter
    @Setter
    @Nullable
    C third;

    public Triplet(@Nullable final A first, @Nullable final B second, @Nullable final C third) {
        super(first, second);
        this.third = third;
    }

    public static <A, B, C> Triplet<A, B, C> of(@Nullable final A first, @Nullable final B second, @Nullable final C third) {
        return new Triplet<>(first, second, third);
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
