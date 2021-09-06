package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Tuple of three values
 * @param <A> Type of first element
 * @param <B> Type of second element
 * @param <C> Type of third element
 */
public class Triplet<A, B, C> extends Pair<A, B> {

    @Getter
    @Setter
    @Nullable
    private C third;

    public Triplet(final @Nullable A first, final @Nullable B second, final @Nullable C third) {
        super(first, second);
        this.third = third;
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
    public int hashCode() {
        return Objects.hash(super.hashCode(), third);
    }
}
