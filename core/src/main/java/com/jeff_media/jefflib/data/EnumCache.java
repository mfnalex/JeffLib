package com.jeff_media.jefflib.data;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Spliterator;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Enum} cache that caches the enum constants and size of the enum
 * @param <E> The enum type
 */
public class EnumCache<E extends Enum<E>> implements Iterable<E> {

    private static final Map<Class<? extends Enum<?>>, EnumCache<?>> CACHE = new HashMap<>();

    private final Class<E> enumClass;
    private final List<E> enumConstants;
    private final int size;

    private EnumCache(Class<E> enumClass) {
        this.enumClass = enumClass;
        this.enumConstants = Collections.unmodifiableList(Arrays.asList(enumClass.getEnumConstants()));
        this.size = enumConstants.size();
    }

    /**
     * Creates a new or returns the cached {@link EnumCache} for the given enum class
     * @param enumClass The enum class
     * @return The {@link EnumCache} for the given enum class
     * @param <E> The enum type
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> EnumCache<E> of(Class<E> enumClass) {
        return (EnumCache<E>) CACHE.computeIfAbsent(enumClass, EnumCache::new);
    }

    @NotNull
    @Override
    public Iterator<E> iterator() {
        return enumConstants.iterator();
    }

    @Override
    public Spliterator<E> spliterator() {
        return enumConstants.spliterator();
    }

    public Class<E> getEnumClass() {
        return this.enumClass;
    }

    /**
     * Returns an unmodifiable list of the enum constants
     * @return An unmodifiable list of the enum constants
     */
    public List<E> getEnumConstants() {
        return this.enumConstants;
    }

    /**
     * Returns the size of the enum
     * @return The size of the enum
     */
    public int getSize() {
        return this.size;
    }
}
