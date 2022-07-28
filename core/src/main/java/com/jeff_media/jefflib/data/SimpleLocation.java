package com.jeff_media.jefflib.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Represents a {@link org.bukkit.Location} like object. Unlike a normal Location, coordinates can only be integers.
 * Yaw and Pitch don't exist at all. This can be used to roughly compare Locations.
 */
@Data
@AllArgsConstructor
public class SimpleLocation {
    private World world;
    private int x, y, z;

    /**
     * Creates a SimpleLocation from a given {@link Location}
     */
    public SimpleLocation(final Location location) {
        this(location.getWorld(), location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }
}
