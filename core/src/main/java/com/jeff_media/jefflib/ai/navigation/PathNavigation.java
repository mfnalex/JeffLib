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

package com.jeff_media.jefflib.ai.navigation;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents the NMS PathNavigation of an entity
 */

public interface PathNavigation {

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@NotNull final BlockVector pos, final double speedModifier) {
        return moveTo(pos.getX(), pos.getY(), pos.getZ(), speedModifier);
    }

    /**
     * Makes the entity move to the specified location
     */
    boolean moveTo(final double x, final double y, final double z, final double speedModifier);

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@NotNull final Entity entity, final double speedModifier) {
        return moveTo(entity.getLocation(), speedModifier);
    }

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@NotNull final Location loc, final double speedModifier) {
        return moveTo(loc.getX(), loc.getY(), loc.getZ(), speedModifier);
    }

    ;

    /**
     * Returns the entity's current target location
     */
    @Nullable
    BlockVector getTargetPos();

    /**
     * Sets the entity's current speed modifier
     *
     * @param speedModifier
     */
    void setSpeedModifier(double speedModifier);

    /**
     * Recomputes the current path
     */
    void recomputePath();

    /**
     * Gets whether the entity arrived at the target location
     */
    boolean isDone();

    /**
     * Gets whether the entity is still walking to its target location
     */
    boolean isInProgress();

    /**
     * Makes the entity stop walking to its current target location
     */
    void stop();

    /**
     * Checks whether the given location is a "stable destination"
     */
    boolean isStableDestination(BlockVector pos);

    /**
     * Sets whether this entity can float
     */
    void setCanFloat(boolean canFloat);

    /**
     * Only available in 1.18 and later
     *
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.18
     */
    boolean shouldRecomputePath(BlockVector pos);

    /**
     * Only available in 1.17 and later
     *
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.17
     */
    float getMaxDistanceToWaypoint();

    /**
     * Only available in 1.16.2 and later
     *
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.16.2
     */
    boolean isStuck();

}
