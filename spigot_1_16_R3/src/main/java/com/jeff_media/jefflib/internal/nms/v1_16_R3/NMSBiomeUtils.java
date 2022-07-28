package com.jeff_media.jefflib.internal.nms.v1_16_R3;

import com.jeff_media.jefflib.data.tuples.Pair;
import lombok.experimental.UtilityClass;
import net.minecraft.server.v1_16_R3.*;
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
        final BlockPosition pos = new BlockPosition(location.getBlockX(), 0, location.getBlockZ());
        final Chunk nmsChunk = ((CraftWorld) location.getWorld()).getHandle().getChunkAtWorldCoords(pos);
        if (nmsChunk != null) {
            return nmsChunk.getBiomeIndex().getBiome(pos.getX(), 0, pos.getZ());
        }
        return null;
    }
}
