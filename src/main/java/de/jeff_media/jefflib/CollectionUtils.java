package de.jeff_media.jefflib;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection related methods. Also contains specific methods for concrete collection types.
 */
public class CollectionUtils {

    /**
     * Creates a List of all given elements
     * @param elements Elements
     * @param <T> Type
     * @return List of all given elements
     */
    public static <T> List<T> createList(T... elements) {
        ArrayList<T> list = new ArrayList<>();
        for(T element : elements) {
            list.add(element);
        }
        return list;
    }

    /**
     * Sorts a map by its values. The value type must implement {@link Comparable}
     * @param map Map to sort
     * @param <K> Key type
     * @param <V> Value type. Must implement {@link Comparable}
     * @return Copy of the given list, sorted by value
     */
    public static <K,V extends Comparable> Map<K, V> sortByEntry(Map<K,V> map) {
        return map.entrySet().stream()
                .sorted((e1,e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Sorts a map by its values according to the given Comparator.
     * @param map Map to sort
     * @param comparator Comparator
     * @param <K> Key type
     * @param <V> Value type
     * @return Copy of the given list, sorted by value
     */
    public static <K,V> Map<K,V> sortByEntry(Map<K,V> map, Comparator<V> comparator) {
        return map.entrySet().stream()
                .sorted((e1,e2) -> comparator.compare(e1.getValue(),e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    /**
     * Gets a random element from a collection
     * @param collection Collection
     * @param <T> Type
     * @return Random element of the collection
     */
    public static <T> T getRandomEntry(Collection<T> collection) {
        int index = JeffLib.getRandom().nextInt(collection.size());
        if(collection instanceof List) {
            return ((List<T>) collection).get(index);
        }
        return (T) collection.toArray()[index];
    }

}
