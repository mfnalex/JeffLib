package com.jeff_media.jefflib.ai;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

/**
 * Represents an NMS pathfinder goal. <b>This is not meant to be implemented by plugins.</b> For custom pathfinder goals,
 * extend {@link CustomGoal}.
 * @see CustomGoal
 */
public interface PathfinderGoal {

    /**
     * Returns whether the goal can be used right now
     */
    boolean canUse();

    /**
     * Gets the Bukkit entity associated with this goal
     * @return
     */
    LivingEntity getBukkitEntity();

    /**
     * Gets called when this goal gets activated
     */
    default void start() {

    }

    /**
     * Gets called when this goal gets deactivated
     */
    default void stop() {

    }

    /**
     * Runs every tick this goal is active
     */
    default void tick() {

    }

    /**
     * Returns whether the goal can continue to be used
     */
    default boolean canContinueToUse() {
        return canUse();
    }

    /**
     * Returns whether this goal is interruptable.
     * Only used in 1.16.2 and later
     */
    default boolean isInterruptable() {
        return true;
    }

    /**
     * Returns whether this goal requires updates every tick.
     * Only used in 1.18 and later
     */
    default boolean requiresUpdateEveryTick() {
        return false;
    }
}
