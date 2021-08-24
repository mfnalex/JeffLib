package de.jeff_media.jefflib;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;

import java.util.*;
import java.util.function.Predicate;

/**
 * Block related methods
 */
public class BlockUtils {

    /**
     * Returns a list of entries containing all BlockData from a given block
     *
     * @param block Block
     * @return List of entries this Block's BlockData contains
     */
    public static List<Map.Entry<String, String>> getBlockDataAsEntries(final Block block) {
        List<Map.Entry<String, String>> list = new ArrayList<>();

        String[] split = block.getBlockData().getAsString().split("\\[");
        if (split.length == 1) {
            return list;
        }
        String info = split[1];
        info = info.substring(0, info.length() - 1);
        String[] entries = info.split(",");
        for (String entry : entries) {
            String key = entry.split("=")[0];
            String value = entry.split("=")[1];
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
    public static List<Block> getBlocksInRadius(Location center, int radius, RadiusType radiusType) {
        return getBlocksInRadius(center, radius, radiusType, block -> true);
    }

    /**
     * Gets all blocks in a given radius around a center location that matches the given Predicate (see also {@link Predicates}
     * <p>
     * Example usage:
     * <pre>
     *     // Get all air blocks within a 10x10x10 cuboid radius around the location
     *     List<Block> nearbyAir = BlockUtils.getBlocksInRadius(location, 10, BlockUtils.RadiusType.CUBOID, BlockUtils.Predicates.AIR);
     *
     *     // Get all blocks with a hardness greater than 1 in a spherical radius
     *     List<Block> nearbyHardBlocks = BlockUtils.getBlocksInRadius(center, 3, RadiusType.SPHERE, block -> block.getType().getHardness()>1);
     * </pre>
     *
     * @param center     Center Location
     * @param radius     Radius
     * @param radiusType RadiusType
     * @param predicate  Predicate to check for
     * @return List of all blocks within the radius that match the given Predicate
     */
    public static List<Block> getBlocksInRadius(Location center, int radius, RadiusType radiusType, Predicate<Block> predicate) {
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
    public static Location getCenter(Block block) {
        return block.getLocation().add(0.5, 0.5, 0.5);
    }

    private static List<Block> getBlocksInRadiusSquare(Location center, int radius, Predicate<Block> predicate) {
        List<Block> blocks = new ArrayList<>();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    Block block = Objects.requireNonNull(center.getWorld()).getBlockAt(x, y, z);
                    if (predicate.test(block)) {
                        blocks.add(block);
                    }
                }
            }
        }
        return blocks;
    }

    private static List<Block> getBlocksInRadiusCircle(Location center, int radius, Predicate<Block> predicate) {
        List<Block> blocks = new ArrayList<>();
        World world = center.getWorld();
        for (int x = center.getBlockX() - radius; x <= center.getBlockX() + radius; x++) {
            for (int y = center.getBlockY() - radius; y <= center.getBlockY() + radius; y++) {
                for (int z = center.getBlockZ() - radius; z <= center.getBlockZ() + radius; z++) {
                    Location location = new Location(world, x, y, z);
                    double distanceSquared = location.distanceSquared(center);
                    if (distanceSquared <= radius * radius) {
                        Block block = location.getBlock();
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
     * Some predefined Block Predicates
     */
    public static class Predicates {
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
