package com.jeff_media.jefflib;

import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import javax.annotation.Nullable;

/**
 * Provides a way to get a random position in a given radius around a given entity's location.
 */
public final class RandomPos {

    /**
     * Gets a random position. Parameters are unknown
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable public static Vector getPos(LivingEntity entity, int var1, int var2) {
        return JeffLib.getNMSHandler().getRandomPos(entity, var1, var2);
    }

    /**
     * Gets a random position. Parameters are unknown
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable public static Vector getPosAway(LivingEntity entity, int var1, int var2, Vector var3) {
        return JeffLib.getNMSHandler().getRandomPosAway(entity, var1, var2, var3);
    }

    /**
     * Gets a random position. Parameters are unknown
     * @param entity The entity to get the position around. Must be a pathfinding entity
     * @return A random position determined by the given parameters, or null if the entity is not a pathfinding entity or when no location could be found
     * @nms Untested in 1.16.5 and older
     */
    @Nullable public static Vector getPosTowards(LivingEntity entity, int var1, int var2, Vector var3, double var4) {
        return JeffLib.getNMSHandler().getRandomPosTowards(entity, var1, var2, var3, var4);
    }

}
