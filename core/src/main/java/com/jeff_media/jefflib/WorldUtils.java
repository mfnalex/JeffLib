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

import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.annotations.Tested;
import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * World related methods
 */
public final class WorldUtils {

    private static final boolean HAS_WORLD_MIN_HEIGHT_METHOD;

    static {
        boolean tmpHasWorldMinHeightMethod;
        try {
            World.class.getMethod("getMinHeight");
            tmpHasWorldMinHeightMethod = true;
        } catch (NoSuchMethodException ignored) {
            tmpHasWorldMinHeightMethod = false;
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
     *
     * @param world         The world
     * @param time          The time
     * @param notifyPlayers Whether to send a time update packet to all players
     * @nms
     */
    @Tested("1.19.4")
    @NMS
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
