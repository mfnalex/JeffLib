package com.jeff_media.jefflib;

import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import java.util.Comparator;

/**
 * Some useful comparators
 */
public abstract class Comparators {

    /**
     * Sorts locations by distance (nearest to farthest)
     */
    public static class LocationByDistanceComparator implements Comparator<Location> {

        private final Location origin;

        public LocationByDistanceComparator(Location origin) {
            this.origin = origin;
        }

        @Override
        public int compare(Location o1, Location o2) {
            return Double.compare(o1.distanceSquared(origin), o2.distanceSquared(origin));
        }
    }

    /**
     * Sorts entities by distance (nearest to farthest)
     */
    public static class EntityByDistanceComparator implements Comparator<Entity> {

        private final Location origin;

        public EntityByDistanceComparator(final @Nonnull Entity origin) {
            this.origin = origin.getLocation();
        }

        public EntityByDistanceComparator(final @Nonnull Location origin) {
            this.origin = origin;
        }

        @Override
        public int compare(Entity o1, Entity o2) {
            return Double.compare(o1.getLocation().distanceSquared(origin), o2.getLocation().distanceSquared(origin));
        }
    }

    /**
     * Sorts blocks by distance (nearest to farthest)
     */
    public static class BlockByDistanceComparator implements Comparator<Block> {

        private final Location origin;

        public BlockByDistanceComparator(final @Nonnull Location origin) {
            this.origin = origin;
        }

        public BlockByDistanceComparator(final @Nonnull Block origin) {
            this.origin = BlockUtils.getCenter(origin);
        }

        @Override
        public int compare(Block o1, Block o2) {
            return Double.compare(BlockUtils.getCenter(o1).distanceSquared(origin), BlockUtils.getCenter(o2).distanceSquared(origin));
        }
    }

}
