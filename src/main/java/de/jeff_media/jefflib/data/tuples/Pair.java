package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Pair<A,B>  {
    @Getter
    @Setter
    @Nullable
    private A first;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair<?, ?> pair = (Pair<?, ?>) o;
        return Objects.equals(first, pair.first) && Objects.equals(second, pair.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }

    @Getter
    @Setter
    @Nullable
    private B second;

    public Pair(@Nullable final A first, @Nullable final B second) {
        this.first=first;
        this.second=second;
    }

}
