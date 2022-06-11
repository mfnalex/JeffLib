package de.jeff_media.jefflib.tests;

import de.jeff_media.jefflib.EnumUtils;
import de.jeff_media.jefflib.UnitTest;
import org.bukkit.Material;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TestEnumUtils extends UnitTest {

    @Test
    void testGetIfPresent() {
        Assertions.assertSame(EnumUtils.getIfPresent(Material.class, "DIRT").orElse(null), Material.DIRT);
    }

    @Test
    void testGetEnumsFromList() {
        final List<Material> materials = EnumUtils.getEnumsFromList(Material.class, Arrays.asList(
                "DIRT", "DIAMOND_PICKAXE", "yo mama", "NETHERITE_SHOVEL"
        ), Collectors.toList());
        Assertions.assertSame(materials.get(0), Material.DIRT);
        Assertions.assertSame(materials.get(1),Material.DIAMOND_PICKAXE);
        Assertions.assertSame(materials.get(2),Material.NETHERITE_SHOVEL);
    }
}
