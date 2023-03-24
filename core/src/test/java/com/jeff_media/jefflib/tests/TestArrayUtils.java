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

import com.jeff_media.jefflib.ArrayUtils;
import com.jeff_media.jefflib.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestArrayUtils extends UnitTest {

    final String[] first = {"0", "1", "2", "3", "4"};
    final String[] second = {"0", "1", "3", "4"};
    final String[] third = {"0", "1", "2", "3", "4", "hello"};
    final String[] combined = {"0", "1", "2", "3", "4", "0", "1", "3", "4", "0", "1", "2", "3", "4"};

    @Test
    public void testRemoveAtIndex() {
        Assertions.assertArrayEquals(ArrayUtils.removeAtIndex(first, 2), second);
    }

    @Test
    public void testAddAfter() {
        Assertions.assertArrayEquals(ArrayUtils.addAfter(first, "hello"), third);
    }

    @Test
    public void testCombine() {
        Assertions.assertArrayEquals(combined, ArrayUtils.combine(first, second, first));
    }

    @Test
    public void testCreate() {
        String[] stringArray0 = ArrayUtils.createArray(String.class);
        String[] stringArray3 = ArrayUtils.createArray(String.class, 3);
        Assertions.assertEquals(0, stringArray0.length);
        Assertions.assertEquals(3, stringArray3.length);
    }
}
