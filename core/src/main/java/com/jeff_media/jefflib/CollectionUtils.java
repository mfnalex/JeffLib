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
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Collection related methods. Also contains specific methods for concrete collection types.
 */
@UtilityClass
public class CollectionUtils {

    /**
     * Creates a List of all given elements
     *
     * @param elements Elements
     * @param <T>      Type
     * @return List of all given elements
     */
    @SafeVarargs
    @Nonnull
    public static <T> List<T> asList(final T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * Creates a list from an iterator
     */
    public static <T> List<T> asList(final Iterator<T> iterator) {
        final List<T> list = new ArrayList<>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    @SafeVarargs
    @Nonnull
    public static <T> Set<T> asSet(final T... elements) {
        final HashSet<T> set = new HashSet<>();
        Collections.addAll(set, elements);
        return set;
    }


    /**
     * Sorts a map by its values. The value type must implement {@link Comparable}
     *
     * @param map Map to sort
     * @param <K> Key type
     * @param <V> Value type. Must implement {@link Comparable}
     * @return Copy of the given list, sorted by value
     */
    public static <K, V extends Comparable<V>> Map<K, V> sortByEntry(final Map<K, V> map) {
        return map.entrySet().stream().sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Sorts a map by its values according to the given Comparator.
     *
     * @param map        Map to sort
     * @param comparator Comparator
     * @param <K>        Key type
     * @param <V>        Value type
     * @return Copy of the given list, sorted by value
     */
    public static <K, V> Map<K, V> sortByEntry(final Map<K, V> map, final Comparator<V> comparator) {
        return map.entrySet().stream().sorted((e1, e2) -> comparator.compare(e1.getValue(), e2.getValue())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Gets a random element from a collection
     *
     * @param collection Collection
     * @param <T>        Type
     * @return Random element of the collection
     */
    public static <T> T getRandomEntry(final Collection<T> collection) {
        final int index = JeffLib.getRandom().nextInt(collection.size());
        if (collection instanceof List) {
            return ((List<T>) collection).get(index);
        }
        //noinspection unchecked
        return (T) collection.toArray()[index];
    }

    /**
     * Returns a list of all loaded Worlds from a config's String list. Worlds can be specified by UUID or by name. Missing worlds will be silently ignored.
     *
     * @param config Configuration file
     * @param node   Path to the string list
     * @return List containing all worlds specified in the list that are currently loaded
     */
    public static List<World> getWorldList(final ConfigurationSection config, final String node) {
        final List<World> list = new ArrayList<>();
        for (final String entry : config.getStringList(node)) {
            World world = Bukkit.getWorld(entry);
            if (world == null) {
                try {
                    final UUID uuid = UUID.fromString(entry);
                    world = Bukkit.getWorld(uuid);
                } catch (final IllegalArgumentException ignored) {

                }
            }
            if (world != null) {
                list.add(world);
            }
        }
        return list;
    }


}
