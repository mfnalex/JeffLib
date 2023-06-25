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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a list of elements with weights. When calling {@link #getRandomElement()}, it returns a random element based on its weight.
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

    @NotNull
    private final List<WeightedElement> list = new ArrayList<>();
    @NotNull
    private final ThreadLocalRandom random = ThreadLocalRandom.current();
    private final Comparator<WeightedElement> COMPARATOR = (o1, o2) -> -Double.compare(o1.weight, o2.weight);
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

    public boolean contains(WeightedElement o) {
        return list.contains(o);
    }

    @NotNull
    public ListIterator<WeightedElement> listIterator() {
        return list.listIterator();
    }

    @NotNull
    public Iterator<WeightedElement> iterator() {
        return list.iterator();
    }

    public boolean add(WeightedElement weightedElement) {
        list.add(weightedElement);
        sortAndIndex();
        return true;
    }

    public boolean add(double weight, E element) {
        return add(new WeightedElement(weight, element));
    }

    public boolean remove(WeightedElement e) {
        boolean changed = list.remove(e);
        if (changed) sortAndIndex();
        return changed;
    }

    public boolean remove(double weight, E element) {
        return remove(new WeightedElement(weight, element));
    }

    public boolean remove(E e) {
        boolean changed = list.removeIf(we -> {
            if (we.element == null) {
                return e == null;
            }
            return we.element.equals(e);
        });
        if (changed) sortAndIndex();
        return changed;
    }

    public boolean addAll(@NotNull Collection<? extends WeightedElement> c) {
        boolean changed = list.addAll(c);
        if (changed) sortAndIndex();
        return changed;
    }

    public void clear() {
        list.clear();
    }

    private void sortAndIndex() {
        list.sort(COMPARATOR);
        currentTotalWeight = list.stream().mapToDouble(WeightedElement::getWeight).sum();
    }

    @Nullable
    public E getRandomElement() {
        if (list.isEmpty()) return null;
        double randomValue = random.nextDouble(currentTotalWeight);
        double currentWeight = 0;
        for (WeightedElement element : list) {
            currentWeight += element.weight;
            if (currentWeight >= randomValue) {
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
     * Represents an element with a weight. Weights are relative to each other, so the total weight of all elements does not matter.
     */
    public class WeightedElement implements Comparable<WeightedRandomList<E>.WeightedElement> {
        private double weight;
        @Nullable
        private E element;

        /**
         * Creates a new WeightedElement
         *
         * @param weight  The weight of the element
         * @param element The element
         */
        public WeightedElement(double weight, @Nullable E element) {
            this.weight = weight;
            this.element = element;
        }

        @Override
        @SuppressWarnings("unchecked")
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            WeightedElement that = (WeightedElement) o;
            return Double.compare(that.weight, weight) == 0 && Objects.equals(element, that.element);
        }

        @Override
        public int hashCode() {
            return Objects.hash(weight, element);
        }

        @Override
        public String toString() {
            return "WeightedElement{" +
                    "chance=" + weight +
                    ", element=" + element +
                    '}';
        }

        /**
         * Returns the weight of the element
         *
         * @return The weight of the element
         */
        public double getWeight() {
            return this.weight;
        }

        /**
         * Sets the weight of the element
         *
         * @param weight The new weight
         */
        public void setWeight(double weight) {
            this.weight = weight;
            WeightedRandomList.this.sortAndIndex();
        }

        /**
         * Returns the element
         *
         * @return The element
         */
        public @Nullable E getElement() {
            return this.element;
        }

        /**
         * Sets the element
         *
         * @param element The new element
         */
        public void setElement(@Nullable E element) {
            this.element = element;
        }

        @Override
        public int compareTo(@NotNull WeightedRandomList<E>.WeightedElement o) {
            return 0;
        }
    }

}
