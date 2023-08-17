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

package com.jeff_media.jefflib.internal.nms.v1_16_R3;

import com.jeff_media.jefflib.data.tuples.Pair;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BlockPosition;
import net.minecraft.server.v1_16_R3.Chunk;
import net.minecraft.server.v1_16_R3.DedicatedServer;
import net.minecraft.server.v1_16_R3.IRegistry;
import net.minecraft.server.v1_16_R3.IRegistryWritable;
import net.minecraft.server.v1_16_R3.MinecraftKey;
import net.minecraft.server.v1_16_R3.ResourceKey;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftServer;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;

@UtilityClass
class NMSBiomeUtils {

    private final ResourceKey<IRegistry<BiomeBase>> BIOME_REGISTRY_RESOURCE_KEY = IRegistry.ay;

    Pair<String, String> getBiomeName(final Location location) {
        final MinecraftKey key = getBiomeKey(location);
        return new Pair<>(key.getNamespace(), key.getKey());
    }

    MinecraftKey getBiomeKey(final Location location) {
        final IRegistryWritable<BiomeBase> registry = getBiomeRegistry();
        return registry.getKey(getBiomeBase(location));
    }

    IRegistryWritable<BiomeBase> getBiomeRegistry() {
        final DedicatedServer dedicatedServer = ((CraftServer) Bukkit.getServer()).getServer();
        return dedicatedServer.getCustomRegistry().b(BIOME_REGISTRY_RESOURCE_KEY);
    }

    BiomeBase getBiomeBase(final Location location) {
        final BlockPosition pos = new BlockPosition(location.getBlockX(), location.getBlockY(), location.getBlockZ());
        final Chunk nmsChunk = ((CraftWorld) location.getWorld()).getHandle().getChunkAtWorldCoords(pos);
        if (nmsChunk != null) {
            return nmsChunk.getBiomeIndex().getBiome(pos.getX(), pos.getY(), pos.getZ());
        }
        return null;
    }
}
