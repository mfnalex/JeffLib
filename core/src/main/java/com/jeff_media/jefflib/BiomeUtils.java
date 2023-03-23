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

package com.jeff_media.jefflib;

import com.jeff_media.jefflib.data.tuples.Pair;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.annotations.Tested;
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
     *
     * @param location Location to check
     * @return NamespacedKey containing the proper biome name
     * @nms 1.16.2+
     */
    @NMS("1.16.2")
    @Tested("1.19.4")
    public NamespacedKey getBiomeNamespacedKey(@NotNull final Location location) {
        final Pair<String, String> keyPair = JeffLib.getNMSHandler().getBiomeName(location);
        return NamespacedKey.fromString(keyPair.getFirst() + ":" + keyPair.getSecond());
    }
}
