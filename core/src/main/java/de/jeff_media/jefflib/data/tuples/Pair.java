package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import javax.annotation.Nullable;

import java.util.Objects;

/**
 * Tuple of two values
 * @param <A> Type of first element
 * @param <B> Type of second element
 */
public class Pair<A, B> {
    @Getter
    @Setter
    @Nullable
    A first;
    @Getter
    @Setter
    @Nullable
    B second;

    public Pair(@Nullable final A first, @Nullable final B second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "Pair{" + "first=" + first + ", second=" + second + '}';
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

}
