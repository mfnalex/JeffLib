package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.worldboundingbox.CuboidWorldBoundingBox;
import de.jeff_media.jefflib.data.worldboundingbox.WorldBoundingBox;
import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.util.BlockVector;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;

/**
 * Block related methods
 */
@UtilityClass
public final class BlockUtils {

    /**
     * Returns the lowest non-air block at a given position
     */
    @Nullable
    public static Block getLowestBlockAt(@NotNull final  World world, final int x, final int z) {
        for(int y = getWorldMinHeight(world); y < world.getMaxHeight(); y++) {
            final Block current = world.getBlockAt(x,y,z);
            if(!current.getType().isAir()) return current;
        }
        return null;
    }

    /**
     * Returns the lowest non-air block at a given location
     */
    @Nullable
    public static Block getLowestBlockAt(@NotNull final Location location) {
        return getLowestBlockAt(Objects.requireNonNull(location.getWorld()), location.getBlockX(), location.getBlockZ());
    }

    /**
     * Returns a list of entries containing all BlockData from a given block
     *
     * @param block Block
     * @return List of entries this Block's BlockData contains
     */
    public static List<Map.Entry<String, String>> getBlockDataAsEntries(final Block block) {
        final List<Map.Entry<String, String>> list = new ArrayList<>();

        final String[] split = block.getBlockData().getAsString().split("\\[");
        if (split.length == 1) {
            return list;
        }
        String info = split[1];
        info = info.substring(0, info.length() - 1);
        final String[] entries = info.split(",");
        for (final String entry : entries) {
            final String key = entry.split("=")[0];
            final String value = entry.split("=")[1];
            list.add(new AbstractMap.SimpleEntry<>(key, value));
        }
        return list;
    }

    /**
     * Gets all blocks in a given radius around a center location
     *
     * @param center     Center Location
     * @param radius     Radius
     * @param radiusType RadiusType
     * @return List of all blocks within the radius
     */
    public static List<Block> getBlocksInRadius(final Location center, final int radius, final RadiusType radiusType) {
        return getBlocksInRadius(center, radius, radiusType, block -> true);
    }

    /**
     * Gets all blocks in a given radius around a center location that matches the given Predicate (see also {@link Predicates}
     * <p>
     * Example usage:
     * <pre>
     *     // Get all air blocks within a 10x10x10 cuboid radius around the location
     *     List&lt;Block&gt; nearbyAir = BlockUtils.getBlocksInRadius(location, 10, BlockUtils.RadiusType.CUBOID, BlockUtils.Predicates.AIR);
     *
     *     // Get all blocks with a hardness greater than 1 in a spherical radius
     *     List&lt;Block&gt; nearbyHardBlocks = BlockUtils.getBlocksInRadius(center, 3, RadiusType.SPHERE, block -> block.getType().getHardness()>1);
     * </pre>
     *
     * @param center     Center Location
     * @param radius     Radius
     * @param radiusType RadiusType
     * @param predicate  Predicate to check for
     * @return List of all blocks within the radius that match the given Predicate
     */
    public static List<Block> getBlocksInRadius(final Location center, final int radius, final RadiusType radiusType, final Predicate<Block> predicate) {
        switch (radiusType) {
            case SPHERE:
                return getBlocksInRadiusCircle(center, radius, predicate);
            case CUBOID:
                return getBlocksInRadiusSquare(center, radius, predicate);
        }
        throw new IllegalArgumentException("Unknown RadiusType: " + radiusType.name());
    }

    /**
     * Returns the center location of a block
     *
     * @param block Block
     * @return Center Location
     */
    public static Location getCenter(final Block block) {
        return block.getLocation().add(0.5, 0.5, 0.5);
    }

    private static List<Block> getBlocksInRadiusSquare(final Location center, final int radius, final Predicate<Block> predicate) {
        final List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Block block = Objects.requireNonNull(center.getWorld()).getBlockAt(x, y, z);
                    if (predicate.test(block)) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksInRadiusCircle(final Location center, final int radius, final Predicate<Block> predicate) {
        final List<Block> blocks = new ArrayList<>();
        final World world = center.getWorld();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    final Location location = new Location(world, x, y, z);
                    final double distanceSquared = location.distanceSquared(center);
                    if (distanceSquared <= radius * radius) {
                        final Block block = location.getBlock();
                        if (predicate.test(block)) {
                            blocks.add(block);
                        }
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * Plays the "composter fill" particle animation and optionally plays the sound if the composter has been filled successfully
     * @param composter Composter block
     * @param success whether the composting chance succeeded
     */
    public static void playComposterFillParticlesAndSound(final Block composter, final boolean success) {
        /*if(composter.getType() != Material.COMPOSTER) {
            throw new IllegalStateException("Given block is not a composter: " + composter.toString());
        }*/
        NMSNotSupportedException.check();
        JeffLib.getNMSHandler().getBlockHandler().playComposterParticlesAndSound(composter, success);
    }

    /**
     * Represents the type of radius
     */
    public enum RadiusType {
        /**
         * A cuboid radius, like a normal WorldEdit selection of X*X*X blocks
         */
        CUBOID,
        /**
         * A sphere radius, for example all blocks within a range of X*X*X blocks that are not further away from the center than the given distance
         */
        SPHERE
    }

    /**
     * Returns the lowest possible building height for a block. It's the same as {@link World#getMinHeight()} but also works on 1.16.4 and earlier
     */
    public static int getWorldMinHeight(final World world) {
        if(McVersion.isAtLeast(1,16,5)) {
            return world.getMinHeight();
        } else {
            return 0;
        }
    }

    /**
     * Turns a given {@link BlockVector} using chunk coordinates into a BlockVector using global coordinates
     * @param chunkVector A BlockVector containing the coordinates inside the chunk (x and z between 0 (inclusive) and 16 (exclusive))
     * @param chunkX X coordinate of the chunk
     * @param y Y coordinate
     * @param chunkZ Z coordinate of the chunk
     * @return A BlockVector representing global coordinates
     */
    public static BlockVector chunkToWorldCoordinates(BlockVector chunkVector, int chunkX, int y, int chunkZ) {
        return new BlockVector(chunkVector.getBlockX() + (chunkX << 4), y, chunkVector.getBlockZ() + (chunkZ << 4));
    }

    /**
     * Gets all {@link ChunkSnapshot}s that are inside or intersect the given {@link BoundingBox}
     * @param onlyLoadedChunks When true, only returns ChunkSnapshots if the related chunk is already loaded. When false, this will force load chunks
     */
    public static List<ChunkSnapshot> getChunkSnapshots(final World world, final BoundingBox box, final boolean onlyLoadedChunks) {
        final int minX = (int) box.getMinX() >> 4;
        final int maxX = (int) box.getMaxX() >> 4;
        final int minZ = (int) box.getMinZ() >> 4;
        final int maxZ = (int) box.getMaxZ() >> 4;

        final List<ChunkSnapshot> chunks = new ArrayList<>();
        for(int x = minX; x <= maxX; x++) {
            for(int z = minZ; z <= maxZ; z++) {
                if(!onlyLoadedChunks || world.isChunkLoaded(x,z)) {
                    final Chunk chunk = world.getChunkAt(x,z);
                    chunks.add(chunk.getChunkSnapshot(true, false, false));
                }
            }
        }
        return chunks;
    }

    /**
     * Gets all {@link Chunk}s that are inside or intersect the given {@link BoundingBox}
     * @param onlyLoadedChunks When true, only returns Chunks if the related chunk is already loaded. When false, this will force load chunks
     */
    public static List<Chunk> getChunks(final World world, final BoundingBox box, final boolean onlyLoadedChunks) {
        final int minX = (int) box.getMinX() >> 4;
        final int maxX = (int) box.getMaxX() >> 4;
        final int minZ = (int) box.getMinZ() >> 4;
        final int maxZ = (int) box.getMaxZ() >> 4;

        final List<Chunk> chunks = new ArrayList<>();
        for(int x = minX; x <= maxX; x++) {
            for(int z = minZ; z <= maxZ; z++) {
                if(!onlyLoadedChunks || world.isChunkLoaded(x,z)) {
                    final Chunk chunk = world.getChunkAt(x,z);
                    chunks.add(chunk);
                }
            }
        }
        return chunks;
    }

    /**
     * Gets a list of all {@link BlockVector}s inside the given {@link BoundingBox} that match the given {@link Predicate}&lt;{@link BlockData}>
     * @param onlyLoadedChunks When true, only returns ChunkSnapshots if the related chunk is already loaded. When false, this will force load chunks
     */
    public static List<BlockVector> getBlocks(final World world, final BoundingBox box, final boolean onlyLoadedChunks, final Predicate<BlockData> predicate) {
        final List<ChunkSnapshot> chunks = getChunkSnapshots(world, box, onlyLoadedChunks);
        final List<BlockVector> blocks = new ArrayList<>();
        final int minHeight = getWorldMinHeight(world);
        for(final ChunkSnapshot chunk : chunks) {
            for(int x = 0; x < 16; x++) {
                for(int z = 0; z < 16; z++) {
                    for(int y = box.getMin().getBlockY(); y < box.getMax().getBlockY(); y++) {
                        if(y > chunk.getHighestBlockYAt(x,z)) break;
                        BlockVector blockVector = new BlockVector(x,y,z);
                        if(!box.contains(chunkToWorldCoordinates(blockVector,chunk.getX(),y,chunk.getZ()))) continue;
                        final BlockData data = chunk.getBlockData(x,y,z);
                        if(predicate.test(data)) {
                            blocks.add(chunkToWorldCoordinates(blockVector,chunk.getX(), y, chunk.getZ()));
                        }
                    }
                }
            }
        }
        return blocks;
    }

    /**
     * Some predefined Block Predicates
     */
    @UtilityClass
    public static final class Predicates {
        /**
         * Represents AIR and CAVE_AIR
         */
        public static final Predicate<Block> AIR = block -> block.getType().isAir();
        /**
         * Represents all blocks except AIR and CAVE_AIR
         */
        public static final Predicate<Block> NOT_AIR = block -> !block.getType().isAir();
        /**
         * Represents all solid blocks
         */
        public static final Predicate<Block> SOLID = block -> block.getType().isSolid();
        /**
         * Represents all non-solid blocks
         */
        public static final Predicate<Block> NOT_SOLID = block -> !block.getType().isSolid();
        /**
         * Represents all blocks affected by gravity
         */
        public static final Predicate<Block> GRAVITY = block -> block.getType().hasGravity();
        /**
         * Represents all blocks not affected by gravity
         */
        public static final Predicate<Block> NO_GRAVITY = block -> !block.getType().hasGravity();
        /**
         * Represents all burnable blocks
         */
        public static final Predicate<Block> BURNABLE = block -> block.getType().isBurnable();
        /**
         * Represents all non-burnable blocks
         */
        public static final Predicate<Block> NOT_BURNABLE = block -> !block.getType().isBurnable();
        /**
         * Represents all interactable blocks
         */
        public static final Predicate<Block> INTERACTABLE = block -> block.getType().isInteractable();
        /**
         * Represents all non-interactable blocks
         */
        public static final Predicate<Block> NOT_INTERACTABLE = block -> !block.getType().isInteractable();
        /**
         * Represents all occluding blocks
         */
        public static final Predicate<Block> OCCLUDING = block -> block.getType().isOccluding();
        /**
         * Represents all non-occluding blocks
         */
        public static final Predicate<Block> NOT_OCCLUDING = block -> !block.getType().isOccluding();
    }

}
