package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Enum related methods
 */
@UtilityClass
public final class EnumUtils {

    private static final Map<Class<? extends Enum<?>>, Set<String>> ENUM_CACHE = new HashMap<>();

    /**
     * Gets an EnumSet of the given Enum constants by their names. Enum constants that aren't found will print a warning.
     * Case is ignored for Bukkit enums.
     */
    public static <E extends Enum<E>> EnumSet<E> getEnumsFromList(final Class<E> enumClazz, final List<String> list) {
        return EnumSet.copyOf(getEnumsFromList(enumClazz, list, Collectors.toSet()));
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
     * Gets a random value of the given Enum class
     */
    public static <E extends Enum<E>> E getRandomElement(final Class<E> enumClazz) {
        final E[] constants = enumClazz.getEnumConstants();
        return constants[RandomUtils.getInt(0, constants.length)];
    }
}

