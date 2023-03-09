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

package com.jeff_media.jefflib;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.bukkit.Bukkit;
import org.bukkit.World;

/**
 * World related methods
 */
public final class WorldUtils {

    private static final boolean HAS_WORLD_MIN_HEIGHT_METHOD;

    static {
        boolean tmpHasWorldMinHeightMethod = false;
        try {
            if (World.class.getMethod("getMinHeight") != null) {
                tmpHasWorldMinHeightMethod = true;
            }
        } catch (NoSuchMethodException ignored) {

        }
        HAS_WORLD_MIN_HEIGHT_METHOD = tmpHasWorldMinHeightMethod;
    }

    /**
     * Gets the default world
     *
     * @nms
     */
    public static @Nonnull World getDefaultWorld() {
        return Objects.requireNonNull(Bukkit.getWorld(getDefaultWorldName()));
    }

    /**
     * Gets the default world name
     *
     * @nms
     */
    public static @Nonnull String getDefaultWorldName() {
        return JeffLib.getNMSHandler().getDefaultWorldName();
    }

    /**
     * Sets the full game time for a world without calling {@link org.bukkit.event.world.TimeSkipEvent}
     */
    public static void setFullTimeWithoutTimeSkipEvent(@Nonnull final World world, final long time, final boolean notifyPlayers) {
        JeffLib.getNMSHandler().setFullTimeWithoutTimeSkipEvent(world, time, notifyPlayers);
    }

    /**
     * Gets the lowest possible building height for a world. It's the same as {@link World#getMinHeight()} but also works on 1.16.4 and earlier
     */
    public static int getWorldMinHeight(final @Nonnull World world) {
        return HAS_WORLD_MIN_HEIGHT_METHOD ? world.getMinHeight() : 0;
    }
}
