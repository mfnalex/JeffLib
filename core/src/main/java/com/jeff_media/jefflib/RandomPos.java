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

import com.jeff_media.jefflib.internal.annotations.NMS;
import javax.annotation.Nullable;
import org.bukkit.entity.Creature;
import org.bukkit.util.Vector;

/**
 * Provides a way to get a random position in a given radius around a given entity's location.
 */
public final class RandomPos {

    /**
     * Gets a random position. Parameters are unknown
     *
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable
    @NMS
    public static Vector getPos(Creature entity, int var1, int var2) {
        return JeffLib.getNMSHandler().getRandomPos(entity, var1, var2);
    }

    /**
     * Gets a random position. Parameters are unknown
     *
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable
    @NMS
    public static Vector getPosAway(Creature entity, int var1, int var2, Vector var3) {
        return JeffLib.getNMSHandler().getRandomPosAway(entity, var1, var2, var3);
    }

    /**
     * Gets a random position. Parameters are unknown
     *
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable
    @NMS
    public static Vector getPosTowards(Creature entity, int var1, int var2, Vector var3, double var4) {
        return JeffLib.getNMSHandler().getRandomPosTowards(entity, var1, var2, var3, var4);
    }

}
