package de.jeff_media.jefflib;

import java.util.*;
import java.util.stream.Collectors;

public class CollectionUtils {

    public static <T> List<T> createList(T... elements) {
        ArrayList<T> list = new ArrayList<>();
        for(T element : elements) {
            list.add(element);
        }
        return list;
    }

    public static <K,V extends Comparable> Map<K, V> sortByEntry(Map<K,V> map) {
        return map.entrySet().stream()
                .sorted((e1,e2) -> -e1.getValue().compareTo(e2.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    }

    public static <T> T getRandomEntry(Collection<T> collection) {
        int index = JeffLib.getRandom().nextInt(collection.size());
        if(collection instanceof List) {
            return ((List<T>) collection).get(index);
        }
        return (T) collection.toArray()[index];
    }

}
