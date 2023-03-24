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

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;

import org.jetbrains.annotations.NotNull;
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

        public EntityByDistanceComparator(final @NotNull Entity origin) {
            this.origin = origin.getLocation();
        }

        public EntityByDistanceComparator(final @NotNull Location origin) {
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

        public BlockByDistanceComparator(final @NotNull Location origin) {
            this.origin = origin;
        }

        public BlockByDistanceComparator(final @NotNull Block origin) {
            this.origin = BlockUtils.getCenter(origin);
        }

        @Override
        public int compare(Block o1, Block o2) {
            return Double.compare(BlockUtils.getCenter(o1).distanceSquared(origin), BlockUtils.getCenter(o2).distanceSquared(origin));
        }
    }

}
