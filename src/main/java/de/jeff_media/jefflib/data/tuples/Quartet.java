package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Tuple of four values
 * @param <A> Type of first element
 * @param <B> Type of second element
 * @param <C> Type of third element
 * @param <D> Type of fourth element
 */
public class Quartet<A, B, C, D> extends Triplet<A, B, C> {

    @Getter
    @Setter
    @Nullable
    private D fourth;

    public Quartet(final @Nullable A first, final @Nullable B second, final @Nullable C third, final @Nullable D fourth) {
        super(first, second, third);
        this.fourth = fourth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Quartet<?, ?, ?, ?> quartet = (Quartet<?, ?, ?, ?>) o;
        return Objects.equals(fourth, quartet.fourth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fourth);
    }
}
