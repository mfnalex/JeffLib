package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection related methods. Also contains specific methods for concrete collection types.
 */
public class CollectionUtils {

    /**
     * Creates a List of all given elements
     *
     * @param elements Elements
     * @param <T>      Type
     * @return List of all given elements
     */
    @SafeVarargs
    public static <T> List<T> createList(T... elements) {
        return new ArrayList<>(Arrays.asList(elements));
    }

    /**
     * Sorts a map by its values. The value type must implement {@link Comparable}
     *
     * @param map Map to sort
     * @param <K> Key type
     * @param <V> Value type. Must implement {@link Comparable}
     * @return Copy of the given list, sorted by value
     */
    public static <K, V extends Comparable> Map<K, V> sortByEntry(Map<K, V> map) {
        //noinspection unchecked
        return map.entrySet().stream()
                .sorted((e1, e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
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
    public static <K, V> Map<K, V> sortByEntry(Map<K, V> map, Comparator<V> comparator) {
        return map.entrySet().stream()
                .sorted((e1, e2) -> comparator.compare(e1.getValue(), e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Gets a random element from a collection
     *
     * @param collection Collection
     * @param <T>        Type
     * @return Random element of the collection
     */
    public static <T> T getRandomEntry(Collection<T> collection) {
        int index = JeffLib.getRandom().nextInt(collection.size());
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
    public static List<World> getWorldList(FileConfiguration config, String node) {
        List<World> list = new ArrayList<>();
        for (String entry : config.getStringList(node)) {
            World world = Bukkit.getWorld(entry);
            if (world == null) {
                try {
                    UUID uuid = UUID.fromString(entry);
                    world = Bukkit.getWorld(uuid);
                } catch (IllegalArgumentException ignored) {

                }
            }
            if (world != null) {
                list.add(world);
            }
        }
        return list;
    }

}
