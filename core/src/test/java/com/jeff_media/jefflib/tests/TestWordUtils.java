package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.WordUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Objects;

public class TestWordUtils {

    @Test
    public void testNiceName() {
        Assertions.assertEquals("Cave Spider", WordUtils.getNiceName(EntityType.CAVE_SPIDER.name()));
        Assertions.assertEquals("Cave Spider", WordUtils.getNiceName(Objects.requireNonNull(NamespacedKey.fromString("namespace:cave_spider"))));
    }
}
