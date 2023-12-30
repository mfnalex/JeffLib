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

package com.jeff_media.jefflib.internal.nms.v1_17_R1;

import com.jeff_media.jefflib.data.tuples.Pair;
import lombok.experimental.UtilityClass;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.LevelChunk;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftServer;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;


@UtilityClass
class NMSBiomeUtils {

    private final ResourceKey<Registry<Biome>> BIOME_REGISTRY_RESOURCE_KEY = Registry.BIOME_REGISTRY;

    Pair<String, String> getBiomeName(final Location location) {
        final ResourceLocation key = getBiomeKey(location);
        return new Pair<>(key.getNamespace(), key.getPath());
    }

    ResourceLocation getBiomeKey(final Location location) {
        final Registry<Biome> registry = getBiomeRegistry();
        return registry.getKey(getBiomeBase(location));
    }

    Registry<Biome> getBiomeRegistry() {
        final DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        return dedicatedServer.registryAccess().registry(BIOME_REGISTRY_RESOURCE_KEY).get();
    }

    Biome getBiomeBase(final Location location) {
        final BlockPos pos = new BlockPos(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        final LevelChunk nmsChunk = ((CraftWorld) location.getWorld()).getHandle().getChunkAt(pos);
        if (nmsChunk != null) {
            return nmsChunk.getBiomes().getNoiseBiome(pos.getX() >> 2, pos.getY() >> 2, pos.getZ() >> 2);
        }
        return null;
    }
}
