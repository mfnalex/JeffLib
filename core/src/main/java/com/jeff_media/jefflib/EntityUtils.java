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

import com.jeff_media.jefflib.ai.goal.GoalSelector;
import com.jeff_media.jefflib.ai.goal.TargetSelector;
import com.jeff_media.jefflib.ai.navigation.Controls;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.nms.AbstractNMSTranslationKeyProvider;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

/**
 * Entity related methods
 */
@UtilityClass
public class EntityUtils {

    private static final double DEFAULT_GENERIC_MOVEMENT_SPEED = 0.25D; // default speed of a generic entity

    /**
     * Gets the closest other player in this world, or null if there is no other player
     */
    @Nullable
    public static Player getClosestOtherPlayer(@Nonnull final Player player) {
        return player.getWorld().getPlayers().stream().filter(other -> other != player).min(new Comparators.EntityByDistanceComparator(player)).orElse(null);
    }

    /**
     * Gets the generic movement speed of an entity
     */
    public static double getMovementSpeed(@Nonnull final LivingEntity entity) {
        final AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if (attribute != null) {
            return attribute.getValue();
        }
        return DEFAULT_GENERIC_MOVEMENT_SPEED;
    }

    /**
     * Gets the closest player in this world, or null if there is no player
     */
    @Nullable
    public static Player getClosestPlayer(@Nonnull final Entity entity) {
        return getClosestPlayer(entity.getLocation());
    }

    /**
     * Gets the closest player in this world, or null if there is no player, or if the world isn't loaded
     */
    @Nullable
    public static Player getClosestPlayer(@Nonnull final Location location) {
        World world = location.getWorld();
        if (world == null) return null;
        return world.getPlayers().stream().min(new Comparators.EntityByDistanceComparator(location)).orElse(null);
    }

    /**
     * Gets the closest player in this world, or null if there is no player
     */
    @Nullable
    public static Player getClosestPlayer(@Nonnull final Block block) {
        return getClosestPlayer(BlockUtils.getCenter(block));
    }

    /**
     * Gets all entities inside {@param min} and {@param max}
     *
     * @param min first corner
     * @param max second corner
     * @return Collection of all entities inside {@param min} and {@param max}
     */
    public static Collection<Entity> getEntities(final Block min, final Block max) {
        final World world = min.getWorld();
        if (!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world!");
        }
        final BoundingBox box = BoundingBox.of(min, max);
        return world.getNearbyEntities(box);
    }

    /**
     * Gets all entities inside min and max that belong to the given EntityType
     *
     * @param min        first corner
     * @param max        second corner
     * @param entityType desired EntityType
     * @return Collection of all entities inside {@param min} and {@param max} that extend {@param entityClass}
     */
    public static Collection<? extends Entity> getEntities(final Block min, final Block max, final EntityType entityType) {
        return getEntities(min, max, entityType.getEntityClass());
    }

    /**
     * Gets all entities inside {@param min} and {@param max} that extend {@param entityClass}
     *
     * @param min         first corner
     * @param max         second corner
     * @param entityClass class the entities have to extend
     * @return Collection of all entities inside {@param min} and {@param max} that extend {@param entityClass}
     */
    public static Collection<? extends Entity> getEntities(final Block min, final Block max, final Class<? extends Entity> entityClass) {
        final World world = min.getWorld();
        if (!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world!");
        }
        final BoundingBox box = BoundingBox.of(min, max);
        final Collection<? extends Entity> entities = world.getEntitiesByClass(entityClass);
        entities.removeIf((Predicate<Entity>) entity -> !box.contains(entity.getLocation().toVector()));
        return entities;
    }

    /**
     * Converts a list of Entities to a list of the desired Entity class. All entities not extending the given class will not be included in the returned list.
     *
     * @param list         Existing list of entities
     * @param desiredClass Desired Entity class
     * @return Collection of all entities of the original list that extend desiredClass
     */
    public static <E extends Entity> List<E> castEntityList(final Iterable<? extends Entity> list, final Class<? extends E> desiredClass) {
        final List<E> newList = new ArrayList<>();
        for (final Entity entity : list) {
            if (desiredClass.isInstance(entity)) {
                //noinspection unchecked
                newList.add((E) entity);
            }
        }
        return newList;
    }

    /**
     * Returns a collection of all entities of the given class, from all worlds
     */
    @Nonnull
    public static <T extends Entity> Collection<T> getEntities(@Nonnull final Class<T> entityClass) {
        final Collection<T> list = new ArrayList<>();
        for (final World world : Bukkit.getWorlds()) {
            list.addAll(world.getEntitiesByClass(entityClass));
        }
        return list;
    }

    /**
     * Gets an entity by its Entity ID
     */
    @Nullable
    public static Entity getEntityById(final int id) {
        for (final Entity entity : getAllEntities()) {
            if (entity.getEntityId() == id) return entity;
        }
        return null;
    }

    /**
     * Returns a collection of all entities from all worlds
     */
    @Nonnull
    public static Collection<Entity> getAllEntities() {
        final Collection<Entity> list = new ArrayList<>();
        for (final World world : Bukkit.getWorlds()) {
            list.addAll(world.getEntities());
        }
        return list;
    }

    /**
     * Gets this mob's {@link GoalSelector}
     */
    @Nonnull
    public static GoalSelector getGoalSelector(@Nonnull final Mob mob) {
        return GoalSelector.of(mob);
    }

    /**
     * Gets this mob's {@link TargetSelector}
     */
    @Nonnull
    public static TargetSelector getTargetSelector(@Nonnull final Mob mob) {
        return TargetSelector.of(mob);
    }

    /**
     * Get this mob's {@link PathNavigation}
     */
    @NMS
    @Nonnull
    public static PathNavigation getNavigation(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getPathNavigation(entity);
    }

    /**
     * Gets this mob's {@link MoveController}
     */
    @NMS
    @Nonnull
    public static MoveController getMoveController(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getMoveControl(entity);
    }

    /**
     * Gets this mob's {@link JumpController}
     */
    @NMS
    @Nonnull
    public static JumpController getJumpController(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getJumpControl(entity);
    }

    /**
     * Gets this mob's {@link LookController}
     */
    @NMS
    @Nonnull
    public static LookController getLookController(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getLookControl(entity);
    }

    /**
     * Gets this mob's {@link com.jeff_media.jefflib.ai.navigation.Controls}
     */
    @NMS
    @Nonnull
    public static Controls getControls(@Nonnull final Mob entity) {
        return Controls.of(entity);
    }

    /**
     * Respawns a player. This does the same thing as {@link Player.Spigot#respawn()} but also works for living players
     */
    @NMS
    public static void respawnPlayer(@Nonnull final Player player) {
        JeffLib.getNMSHandler().respawnPlayer(player);
    }


    /**
     * Gets the translation key associated with this EntityType
     * @nms Requires NMS in versions below latest 1.19.3
     */
    @NMS
    @NotNull public static String getTranslationKey(@NotNull final EntityType entityType) {
        if(ServerUtils.hasTranslationKeyProvider()) {
            return entityType.getTranslationKey();
        } else {
            if(JeffLib.getNMSHandler() instanceof AbstractNMSTranslationKeyProvider) {
                return ((AbstractNMSTranslationKeyProvider) JeffLib.getNMSHandler()).getTranslationKey(entityType);
            } else {
                throw new NMSNotSupportedException("This version of NMS does not support getting the translation key of an EntityType");
            }
        }
    }

}
