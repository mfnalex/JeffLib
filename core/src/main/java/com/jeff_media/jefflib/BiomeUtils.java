package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.annotations.NMS;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;

import javax.annotation.Nonnull;

/**
 * Biome related methods
 */
@UtilityClass
public class BiomeUtils {

    /**
     * Returns the proper NamespacedKey for the biome at the given location, e.g. "minecraft:desert" or "terralith:salt_flats"
     *
     * @param location Location to check
     * @return NamespacedKey containing the proper biome name
     * @nms 1.16.2+
     */
    @NMS("1.16.2")
    public NamespacedKey getBiomeNamespacedKey(@Nonnull final Location location) {
        final Pair<String, String> keyPair = JeffLib.getNMSHandler().getBiomeName(location);
        return NamespacedKey.fromString(keyPair.getFirst() + ":" + keyPair.getSecond());
    }
}
