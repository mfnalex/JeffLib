package com.jeff_media.jefflib.ai;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

public interface PathfinderGoal {
    boolean canUse();

    LivingEntity getBukkitEntity();

    default void start() {

    }
    default void stop() {

    }
    default void tick() {

    }

    default boolean canContinueToUse() {
        return canUse();
    }

    default boolean isInterruptable() {
        return true;
    }

    default boolean requiresUpdateEveryTick() {
        return false;
    }
}
