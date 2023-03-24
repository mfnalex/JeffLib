/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Enum related methods
 */
@UtilityClass
public class EnumUtils {

    private static final Map<Class<? extends Enum<?>>, Set<String>> ENUM_CACHE = new HashMap<>();
    private static final Map<Class<? extends Enum<?>>, List<? extends Enum<?>>> ENUM_ARRAY_CACHE = new HashMap<>();
    private static final Map<Class<? extends Enum<?>>, EnumMap<?, ?>> NEXT_ENUMS = new HashMap<>();

    /**
     * Gets an EnumSet of the given Enum constants by their names. Enum constants that aren't found will print a warning.
     * Case is ignored for Bukkit enums.
     */
    public static <E extends Enum<E>> EnumSet<E> getEnumsFromListAsEnumSet(final Class<E> enumClazz, final List<String> list) {
        return getEnumsFromList(enumClazz, list, Collectors.toCollection(() -> EnumSet.noneOf(enumClazz)));
    }

    /**
     * Gets a Set of the given Enum constants by their names. Enum constants that aren't found will print a warning.
     * Case is ignored for Bukkit enums.
     */
    public static <E extends Enum<E>> Set<E> getEnumsFromListAsSet(final Class<E> enumClazz, final List<String> list) {
        return getEnumsFromList(enumClazz, list, Collectors.toSet());
    }

    /**
     * Gets a Collection of the given Enum constants by their names. Enums constants that aren't found will print a warning.
     * Case is ignored for Bukkit enums.
     */
    public static <E extends Enum<E>, C extends Collection<E>> C getEnumsFromList(final Class<E> enumClazz, final List<String> list, final Collector<? super E, ?, C> collector) {
        return list.stream().map(entry -> {
            final Optional<E> result = getIfPresent(enumClazz, enumClazz.getName().startsWith("org.bukkit") ? entry.toUpperCase(Locale.ROOT) : entry);
            if (!result.isPresent()) {
                JeffLib.getPlugin().getLogger().severe("Could not find " + enumClazz.getSimpleName() + ": '" + entry + "'");
                return null;
            }
            return result.get();
        }).filter(Objects::nonNull).collect(collector);
    }

    /**
     * Gets an {@link Optional} of a given Enum by its name
     */
    public static <E extends Enum<E>> Optional<E> getIfPresent(final Class<E> enumClazz, final String value) {
        final Set<String> enumSet = ENUM_CACHE.computeIfAbsent(enumClazz, EnumUtils::toStringSet);
        return Optional.ofNullable(enumSet.contains(value) ? Enum.valueOf(enumClazz, value) : null);
    }

    private static Set<String> toStringSet(final Class<? extends Enum<?>> enumClazz) {
        return Arrays.stream(enumClazz.getEnumConstants()).map(Enum::toString).collect(Collectors.toSet());
    }

    /**
     * Gets an EnumSet of the given Enum constants by a list of regex patterns. Example:
     * <pre>
     * materials:
     * - "^((.+)_)*CHEST$" # matches CHEST, TRAPPED_CHEST, etc
     * - "^((.+)_)*SHULKER_BOX$" # matches SHULKER_BOX, RED_SHULKER_BOX, etc
     * - "^BARREL$" # matches only BARREL
     * </pre>
     */
    public static <E extends Enum<E>> EnumSet<E> getEnumsFromRegexList(final Class<E> enumClazz, final List<String> list) {
        final EnumSet<E> result = EnumSet.noneOf(enumClazz);
        for (final String regex : list) {
            final Pattern pattern = Pattern.compile(regex);
            for (final E e : enumClazz.getEnumConstants()) {
                if (result.contains(e)) continue;
                final String name = e.name();
                if (pattern.matcher(name).matches()) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    /**
     * Gets a random value of the given Enum class. Values are cached, so it doesn't have to call values() all the time.
     */
    public static <E extends Enum<E>> E getRandomElement(final Class<E> enumClazz) {
        final List<E> values = getValues(enumClazz);
        return values.get(JeffLib.getThreadLocalRandom().nextInt(values.size()));
    }

    /**
     * Returns all elements of the given enum class. Unlike calling values() on an element instance,
     * or calling getEnumConstants() on an enum class, this will cache the delivered array and
     * doesn't have to create a new one everytime.
     * The returned list is unmodifiable.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> List<E> getValues(final Class<E> enumClazz) {
        List<E> values = (List<E>) ENUM_ARRAY_CACHE.get(enumClazz);
        if (values == null) {
            values = Collections.unmodifiableList(Arrays.asList(enumClazz.getEnumConstants()));
            ENUM_ARRAY_CACHE.put(enumClazz, values);
        }
        return values;
    }

    /**
     * Gets the next element of the given enum class by its ordinal.
     * For example, if your enum class has three declared values A, B and C, then calling this method with A will return B,
     * calling it with B will return C, and calling it with C will return A.
     * The next element of each element is cached and does not require to call values() all the time.
     */
    @SuppressWarnings("unchecked")
    public static <E extends Enum<E>> E getNextElement(final E e) {
        final Class<E> enumClazz = (Class<E>) e.getClass();
        final EnumMap<E, E> nextEnums = (EnumMap<E, E>) NEXT_ENUMS.computeIfAbsent(enumClazz, __ -> new EnumMap<E, E>(enumClazz));
        E next = nextEnums.get(e);
        if (next == null) {
            final int ordinal = e.ordinal();
            final List<E> values = getValues(enumClazz);
            next = values.get((ordinal + 1) % values.size());
            nextEnums.put(e, next);
        }
        return next;
    }


}

