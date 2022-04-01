package de.jeff_media.jefflib;

import org.bukkit.Material;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class EnumUtils {

    private static final Map<Class<? extends Enum<?>>, Set<String>> ENUM_CACHE = new HashMap<>();

    public static <E extends Enum<E>> Optional<E> getIfPresent(final Class<E> enumClazz, final String value) {
        final Set<String> enumSet = ENUM_CACHE.computeIfAbsent(enumClazz, EnumUtils::toStringSet);
        return Optional.ofNullable(enumSet.contains(value) ? Enum.valueOf(enumClazz, value) : null);
    }

    private static Set<String> toStringSet(final Class<? extends Enum<?>> enumClazz) {
        return Arrays.stream(enumClazz.getEnumConstants()).map(Enum::toString).collect(Collectors.toSet());
    }

    public static <E extends Enum<E>> List<E> getEnumsFromList(final Class<E> enumClazz, final List<String> list) {
        return getEnumsFromList(enumClazz, list, Collectors.toList());
    }

    public static <E extends Enum<E>> List<E> getEnumsFromRegexList(final Class<E> enumClazz, final List<String> list) {
        List<E> result = new ArrayList<>();
        for(String regex : list) {
            Pattern pattern = Pattern.compile(regex);
            for(E e : enumClazz.getEnumConstants()) {
                if(result.contains(e)) continue;
                String name = e.name();
                if(pattern.matcher(name).matches()) {
                    result.add(e);
                }
            }
        }
        return result;
    }

    public static <E extends Enum<E>, C extends Collection<E>> C getEnumsFromList(final Class<E> enumClazz,
                                                                                  final List<String> list,
                                                                                  final Collector<? super E, ?, C> collector) {
        return list.stream()
                .map(entry -> {
                    final Optional<E> result = getIfPresent(enumClazz, enumClazz.getName().startsWith("org.bukkit") ? entry.toUpperCase(Locale.ROOT) : entry);
                    if (!result.isPresent()) {
                        JeffLib.getPlugin().getLogger().severe("Could not find " + enumClazz.getSimpleName() + ": '" + entry + "'");
                        return null;
                    }
                    return result.get();
                })
                .filter(Objects::nonNull)
                .collect(collector);
    }
}

