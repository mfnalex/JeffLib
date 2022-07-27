package com.jeff_media.jefflib;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;

import java.util.Comparator;
import java.util.List;

public class Comparators {

    /**
     * Sorts locations by distance (nearest to farthest)
     */
    public static Comparator<Location> getLocationsByDistanceComparator(final Location origin) {
        return Comparator.comparingInt(o -> (int) origin.distanceSquared(o));
    }

    /**
     * Sorts entities by distance (nearest to farthest)
     */
    public static Comparator<Entity> getEntitiesByDistanceComparator(final Entity origin) {
        return Comparator.comparingInt(o -> (int) origin.getLocation().distanceSquared(o.getLocation()));
    }
}
