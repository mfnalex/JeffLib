package de.jeff_media.jefflib.data.tuples;

import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Quintet<?, ?, ?, ?, ?> quintet = (Quintet<?, ?, ?, ?, ?>) o;
        return Objects.equals(fifth, quintet.fifth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), fifth);
    }
}
