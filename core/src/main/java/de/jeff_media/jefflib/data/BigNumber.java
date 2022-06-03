package de.jeff_media.jefflib.data;

import java.util.HashMap;
import java.util.Map;

@Deprecated
public class BigNumber extends Number {

    private static final Map<Long, String> SUFFIXES = new HashMap<>();

    static {
        SUFFIXES.put(1_000L,"k");
        SUFFIXES.put(1_000_000L, "M");
        SUFFIXES.put(1_000_000_000L,"B");
        SUFFIXES.put(1_000_000_000_000L,"T");
        SUFFIXES.put(1_000_000_000_000_000L, "Q");
    }

    private final double value;

    public BigNumber(final double value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return (int) value;
    }

    @Override
    public long longValue() {
        return (long) value;
    }

    @Override
    public float floatValue() {
        return (float) value;
    }

    @Override
    public double doubleValue() {
        return value;
    }


}
