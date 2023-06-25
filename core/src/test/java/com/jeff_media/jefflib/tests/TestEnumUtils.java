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

import com.jeff_media.jefflib.EnumUtils;
import com.jeff_media.jefflib.UnitTest;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.bukkit.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestEnumUtils extends UnitTest {

    @Test
    void testGetIfPresent() {
        Assertions.assertSame(EnumUtils.getIfPresent(Material.class, "DIRT").orElse(null), Material.DIRT);
    }

    @Test
    void testGetEnumsFromList() {
        final List<Material> materials = EnumUtils.getEnumsFromList(Material.class, Arrays.asList("DIRT", "DIAMOND_PICKAXE", "yo mama", "NETHERITE_SHOVEL"), Collectors.toList());
        Assertions.assertSame(materials.get(0), Material.DIRT);
        Assertions.assertSame(materials.get(1), Material.DIAMOND_PICKAXE);
        Assertions.assertSame(materials.get(2), Material.NETHERITE_SHOVEL);
    }

    @Test
    void testNextEnum() {
        Assertions.assertSame(TestEnum.B, EnumUtils.getNextElement(TestEnum.A));
        Assertions.assertSame(TestEnum.C, EnumUtils.getNextElement(TestEnum.B));
        Assertions.assertSame(TestEnum.A, EnumUtils.getNextElement(TestEnum.C));
    }

    private enum TestEnum {
        A, B, C
    }
}
