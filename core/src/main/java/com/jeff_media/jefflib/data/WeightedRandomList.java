package com.jeff_media.jefflib.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import javax.annotation.Nonnull;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a list of elements with weights. When calling {@link #getRandomElement()}, it returns a random element based on it's weight.
 * <p>
 * Example: You have the following elements in the list (Element -> Weight):
 * <pre>A -> 5.0
 * B -> 3.0
 * C -> 2.0</pre>
 *
 * If you now call {@link #getRandomElement()}, it will return A 50% times, B 30% times and C 20% times.
 * <p>
 * The total weight of all elements added together does not matter.
 *
 * @param <E> The type of the elements
 */
@SuppressWarnings("unused")
public class WeightedRandomList<E> {

    @Nonnull private final List<WeightedElement<E>> list = new ArrayList<>();
    @Nonnull private final ThreadLocalRandom random = ThreadLocalRandom.current();

    private double currentTotalWeight = 0;

    public int size() {
        return list.size();
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean contains(E o) {
        return list.stream().anyMatch(e -> Objects.equals(o, e.element));
    }

    public boolean contains(WeightedElement<E> o) {
        return list.contains(o);
    }

    @NotNull
    public ListIterator<WeightedElement<E>> listIterator() {
        return list.listIterator();
    }

    @NotNull
    public Iterator<WeightedElement<E>> iterator() {
        return list.iterator();
    }

    public boolean add(WeightedElement<E> weightedElement) {
        list.add(weightedElement);
        sortAndIndex();
        return true;
    }

    public boolean add(double weight, E element) {
        return add(new WeightedElement<>(weight, element));
    }

    public boolean remove(WeightedElement<E> e) {
        boolean changed = list.remove(e);
        if(changed) sortAndIndex();
        return changed;
    }

    public boolean remove(double weight, E element) {
        return remove(new WeightedElement<>(weight, element));
    }

    public boolean remove(E e) {
        boolean changed = list.removeIf(we -> {
            if(we.element == null) {
                return e == null;
            }
            return we.element.equals(e);
        });
        if(changed) sortAndIndex();
        return changed;
    }

    public boolean addAll(@NotNull Collection<? extends WeightedElement<E>> c) {
        boolean changed = list.addAll(c);
        if(changed) sortAndIndex();
        return changed;
    }

    public void clear() {
        list.clear();
    }

    private void sortAndIndex() {
        list.sort(WeightedElement.COMPARATOR);
        currentTotalWeight = list.stream().mapToDouble(WeightedElement::getChance).sum();
    }

    @Nullable
    public E getRandomElement() {
        if(list.isEmpty()) return null;
        double randomValue = random.nextDouble(currentTotalWeight);
        double currentWeight = 0;
        for(WeightedElement<E> element : list) {
            currentWeight += element.chance;
            if(currentWeight >= randomValue) {
                return element.element;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WeightedRandomList<?> that = (WeightedRandomList<?>) o;
        return list.equals(that.list);
    }

    @Override
    public int hashCode() {
        return Objects.hash(list);
    }

    @Override
    public String toString() {
        return Arrays.toString(list.toArray());
    }

    /**
     * Represents an element with a weight.
     * @param <E> The type of the element
     */
    public static final class WeightedElement<E> implements Comparable<WeightedElement<E>>, Comparator<WeightedElement<E>> {

        private static final Comparator<WeightedElement<?>> COMPARATOR = (o1, o2) -> -Double.compare(o1.chance, o2.chance);

        @Getter @Setter private double chance;

        @Nullable @Getter @Setter private E element;

        private WeightedElement(double chance, @Nullable E element) {
            this.chance = chance;
            this.element = element;
        }

        @Override
        public int compareTo(@NotNull WeightedRandomList.WeightedElement<E> o) {
            return COMPARATOR.compare(this, o);
        }

        @Override
        public int compare(WeightedElement<E> o1, WeightedElement<E> o2) {
            return COMPARATOR.compare(o1, o2);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeightedElement<?> that = (WeightedElement<?>) o;
            return Double.compare(that.chance, chance) == 0 && Objects.equals(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(chance, element);
        }

        @Override
        public String toString() {
            return "WeightedElement{" +
                    "chance=" + chance +
                    ", element=" + element +
                    '}';
        }
    }

}
