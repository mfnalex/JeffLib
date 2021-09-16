package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.jetbrains.annotations.NotNull;

/**
 * Biome related methods
 */
@UtilityClass
public class BiomeUtils {

    /**
     * Returns the proper NamespacedKey for the biome at the given location, e.g. "minecraft:desert" or "terralith:salt_flats"
     * @param location Location to check
     * @return NamespacedKey containing the proper biome name
     */
    public NamespacedKey getBiomeNamespacedKey(@NotNull final Location location) {
        NMSNotSupportedException.check();
        final Pair<String,String> keyPair = JeffLib.getNMSHandler().getBiomeName(location);
        return NamespacedKey.fromString(keyPair.getFirst()+":"+keyPair.getSecond());
    }
}
