/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.McVersion;
import com.jeff_media.jefflib.UnitTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestMcVersion extends UnitTest {

    @Test
    public void testIsAtLeast() {

        final McVersion v1_8_8 = new McVersion(1, 8, 8);
        final McVersion v1_17 = new McVersion(1, 17);
        final McVersion v1_18 = new McVersion(1, 18);
        final McVersion v1_18_1 = new McVersion(1, 18, 1);
        final McVersion v1_18_2 = new McVersion(1, 18, 2);
        final McVersion v1_19 = new McVersion(1, 19);

        Assertions.assertEquals(v1_8_8.toString(), "1.8.8");
        Assertions.assertEquals(v1_19.toString(), "1.19");

        Assertions.assertTrue(v1_18.isAtLeast(v1_18));
        Assertions.assertFalse(v1_18.isAtLeast(v1_18_1));
        Assertions.assertTrue(v1_18.isAtLeast(v1_17));

        Assertions.assertEquals(v1_18_2, new McVersion(1, 18, 2));

        //Assertions.assertEquals(McVersion.current(), v1_18);
    }

    @Test
    public void testComparable() {
        final McVersion v1_8_8 = new McVersion(1, 8, 8);
        final McVersion v1_18 = new McVersion(1, 18);
        final McVersion v1_18_2 = new McVersion(1, 18, 2);
        final McVersion v1_19 = new McVersion(1, 19);

        final List<McVersion> unsorted = Arrays.asList(v1_18, v1_8_8, v1_19, v1_18_2);
        final McVersion[] sorted = new ArrayList<>(unsorted).stream().sorted().toArray(McVersion[]::new);
        Assertions.assertArrayEquals(sorted, new McVersion[] {v1_8_8, v1_18, v1_18_2, v1_19});
    }

    @Test
    public void testToString() {
        Assertions.assertEquals("1.19.1", new McVersion(1, 19, 1).toString());
        Assertions.assertEquals("1.17", new McVersion(1, 17).toString());
        Assertions.assertEquals("1.17", new McVersion(1, 17, 0).getName());
    }
}
