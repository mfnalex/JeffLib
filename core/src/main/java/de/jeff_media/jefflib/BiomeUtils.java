package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class BiomeUtils {
    public Pair<String,String> getBiomeNamespacedKey(@NotNull final Location location) {
        NMSNotSupportedException.check();
        return JeffLib.getNMSHandler().getBiomeName(location);
    }
}
