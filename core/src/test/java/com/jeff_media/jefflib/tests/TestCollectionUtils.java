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

import com.jeff_media.jefflib.CollectionUtils;
import com.jeff_media.jefflib.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class TestCollectionUtils extends UnitTest {

    @Test
    public void testSortMapByKey() {
        Map<String,Integer> map = new HashMap<>();
        map.put("b",2);
        map.put("a",1);
        map.put("c",3);
        Map<String,Integer> sortedMap = new HashMap<>();
        sortedMap.put("a",1);
        sortedMap.put("b",2);
        sortedMap.put("c",3);

        Assertions.assertEquals(sortedMap, CollectionUtils.sortByEntry(map));
    }

}
