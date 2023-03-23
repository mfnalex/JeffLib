/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.pluginhooks.worldguard;

import com.jeff_media.jefflib.PluginUtils;
import com.jeff_media.jefflib.exceptions.MissingPluginException;
import com.jeff_media.jefflib.internal.annotations.RequiresPlugin;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * WorldGuard related methods. Can be safely used even when WorldGuard is not installed, as long as you catch the {@link MissingPluginException}
 */
@UtilityClass
public class WorldGuardUtils {

    /**
     * Checks whether WorldGuard is installed and enabled
     *
     * @return true when WorldGuard is installed and enabled, otherwise false
     */
    public static boolean isWorldGuardInstalled() {
        return PluginUtils.isInstalledAndEnabled("WorldGuard");
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
    @RequiresPlugin("WorldGuard")
    public static boolean isInsideRegion(final Location location, final String regionName) throws MissingPluginException {
        try {
            return getRegionsAtLocation(location).contains(regionName);
        } catch (final Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }

    /**
     * Gets a collection of all region names at a specific location
     *
     * @param location Location to check
     * @return Collection of all region names at this location
     * @throws MissingPluginException exception
     */
    @Nonnull
    @RequiresPlugin("WorldGuard")
    public static Collection<String> getRegionsAtLocation(final Location location) throws MissingPluginException {
        try {
            return WorldGuardHandler.getRegionsAtLocation(location);
        } catch (final Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }

    public static boolean canPlace(@Nonnull final Player player, @Nonnull final Location location) throws MissingPluginException {
        if (isWorldGuardInstalled()) {
            return WorldGuardHandler.canPlace(player, location);
        }
        return true;
    }

    @RequiresPlugin("WorldGuard")
    public static boolean canInteract(@Nonnull final Player player, @Nonnull final Location location) throws MissingPluginException {
        try {
            return WorldGuardHandler.canInteract(player, location);
        } catch (final Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }

    @RequiresPlugin("WorldGuard")
    public static boolean canBreak(@Nonnull final Player player, @Nonnull final Location location) throws MissingPluginException {
        try {
            return WorldGuardHandler.canBreak(player, location);
        } catch (final Throwable t) {
            throw new MissingPluginException("WorldGuard");
        }
    }

    @Nonnull
    public static StateFlag registerStateFlag(@Nonnull String name, @Nonnull StateFlag.State defaultValue) {
        if (isWorldGuardInstalled()) {
            return WorldGuardHandler.registerStateFlag(name, defaultValue);
        }
        return new StateFlag(name, StateFlag.State.DENY);
    }

    @Nonnull
    public static StateFlag.State testStateFlag(@Nullable Player player, @Nonnull Location location, @Nonnull StateFlag flag) {
        if (isWorldGuardInstalled()) {
            return StateFlag.State.fromBoolean(WorldGuardHandler.testStateFlag(player, location, flag));
        }
        return flag.getDefaultValue();
    }


}
