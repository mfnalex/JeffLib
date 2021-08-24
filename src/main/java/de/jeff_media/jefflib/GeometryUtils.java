package de.jeff_media.jefflib;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;

/**
 * Geometry related methods
 */
public class GeometryUtils {

    /**
     * Gets a set of all Locations in a specific distance describing a hollow cube around two locations
     *
     * @param min      First location
     * @param max      Sedond location
     * @param distance Distance between locations
     * @return Set of all locations describing a hollow cube around both locations
     */
    public static Set<Location> getHollowCube(Location min, Location max, double distance) {
        Set<Location> result = new HashSet<>();
        World world = min.getWorld();
        double minX = Math.min(min.getX(), max.getX());
        double minY = Math.min(min.getY(), max.getY());
        double minZ = Math.min(min.getZ(), max.getZ());
        double maxX = Math.max(min.getX(), max.getX());
        double maxY = Math.max(min.getY(), max.getY());
        double maxZ = Math.max(min.getZ(), max.getZ());

        for (double x = minX; x <= maxX; x += distance) {
            for (double y = minY; y <= maxY; y += distance) {
                for (double z = minZ; z <= maxZ; z += distance) {
                    int components = 0;
                    if (x == minX || x == maxX) components++;
                    if (y == minY || y == maxY) components++;
                    if (z == minZ || z == maxZ) components++;
                    if (components >= 2) {
                        result.add(new Location(world, x, y, z));
                    }
                }
            }
        }

        return result;
    }
}
