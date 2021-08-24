package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.MissingPluginException;
import de.jeff_media.jefflib.internal.blackhole.WorldGuardHandler;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * WorldGuard related methods. Can be safely used even when WorldGuard is not installed, as long as you catch the {@link MissingPluginException}
 */
public class WorldGuardUtils {

    /**
     * Gets a collection of all region names at a specific location
     *
     * @param location Location to check
     * @return Collection of all region names at this location
     * @throws MissingPluginException exception
     */
    public static @NotNull Collection<String> getRegionsAtLocation(Location location) throws MissingPluginException {
        try {
            return WorldGuardHandler.getRegionsAtLocation(location);
        } catch (Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }

    /**
     * Checks whether a location is inside a region of the given name/id
     * <p>
     * Example Usage:
     *
     * <pre>
     *     if(WorldGuardUtils.isInsideRegion(player.getLocation(), "my-region") {
     *         System.out.println("The player is inside the region \"my-region\"!");
     *     }
     * </pre>
     *
     * @param location   Location to check
     * @param regionName Name of the region to check
     * @return true when the location is inside the given region, otherwise false
     * @throws MissingPluginException exception
     */
    public static boolean isInsideRegion(Location location, String regionName) throws MissingPluginException {
        try {
            return getRegionsAtLocation(location).contains(regionName);
        } catch (Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }
}
