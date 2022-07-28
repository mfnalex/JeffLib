package com.jeff_media.jefflib.ai;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.util.BlockVector;

import javax.annotation.Nonnull;

/**
 * Represents the NMS PathNavigation of an entity
 */
public interface PathNavigation {

    /**
     * Makes the entity move to the specified location
     */
    boolean moveTo(final double x, final double y, final double z, final double speedModifier);

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@Nonnull final Location loc, final double speedModifier) {
        return moveTo(loc.getX(), loc.getY(), loc.getZ(), speedModifier);
    };

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@Nonnull final BlockVector pos, final double speedModifier) {
        return moveTo(pos.getX(), pos.getY(), pos.getZ(), speedModifier);
    };

    /**
     * Makes the entity move to the specified location
     */
    default boolean moveTo(@Nonnull final Entity entity, final double speedModifier) {
        return moveTo(entity.getLocation(), speedModifier);
    };

    /**
     * Returns the entity's current target location
     */
    BlockVector getTargetPos();

    /**
     * Sets the entity's current speed modifier
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
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.18
     */
    boolean shouldRecomputePath(BlockVector pos);

    /**
     * Only available in 1.17 and later
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.17
     */
    float getMaxDistanceToWaypoint();

    /**
     * Only available in 1.16.2 and later
     * @throws com.jeff_media.jefflib.exceptions.NMSNotSupportedException on versions prior to 1.16.2
     */
    boolean isStuck();

}
