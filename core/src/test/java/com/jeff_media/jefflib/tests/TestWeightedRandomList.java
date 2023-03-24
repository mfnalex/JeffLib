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

package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.Assertions2;
import com.jeff_media.jefflib.UnitTest;
import com.jeff_media.jefflib.data.WeightedRandomList;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestWeightedRandomList extends UnitTest {

    private static final int LOOPS = 100_000;

    @Test
    public void testProbability() {
        //for(int j = 0; j < 100; j++) {
        //System.out.println("Testing WeightedRandomList #" + j);
        WeightedRandomList<String> list = new WeightedRandomList<>();
        list.add(9, "a");
        list.add(90, "b");
        list.add(list.new WeightedElement(1, "c"));
        Map<String, Integer> map = new HashMap<>();
        System.out.println(list);
        for (int i = 0; i < LOOPS; i++) {
            String s = list.getRandomElement();
            map.put(s, map.getOrDefault(s, 0) + 1);
        }
        Map<String, Double> results = new HashMap<>();
        for (Map.Entry<String, Integer> entry : map.entrySet()) {
            int amount = entry.getValue();
            double percent = amount / (double) LOOPS * 100.0;
            String percentStr = String.format("%.2f", percent);
            System.out.println(entry.getKey() + ": " + amount + "(" + percentStr + "%)");
            results.put(entry.getKey(), percent);
        }
        Assertions2.assertBetween(8.5, 9.5, results.get("a"));
        Assertions2.assertBetween(89.5, 90.5, results.get("b"));
        Assertions2.assertBetween(0.5, 1.5, results.get("c"));
        //}
    }
}
