package com.jeff_media.jefflib;

import com.jeff_media.jefflib.ai.MoveController;
import com.jeff_media.jefflib.ai.PathNavigation;
import com.jeff_media.jefflib.ai.PathfinderGoal;
import com.jeff_media.jefflib.internal.annotations.NMS;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.entity.*;
import org.bukkit.util.BoundingBox;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Entity related methods
 */
@UtilityClass
public final class EntityUtils {

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
     * Gets the closest player in this world, or null if there is no player
     */
    @Nullable
    public static Player getClosestPlayer(@Nonnull final Location location) {
        return location.getWorld().getPlayers().stream().min(new Comparators.EntityByDistanceComparator(location)).orElse(null);
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
     * Adds a {@link PathfinderGoal} to an entity
     *
     * @param entity   Entity to add the goal to
     * @param goal     Goal to add
     * @param priority Priority of the goal
     * @return true if the goal was added successfully, false if the given entity is not a pathfinding mob (e.g. Bats)
     * @throws UnsupportedOperationException If the given PathfinderGoal is not supposed
     */
    public static boolean addPathfinderGoal(@Nonnull final Mob entity, @Nonnull final PathfinderGoal goal, final int priority) {
        return JeffLib.getNMSHandler().addGoal(entity, goal, priority);
    }

    /**
     * Returns the {@link PathNavigation} for this entity
     */
    @NMS
    @Nonnull
    public static PathNavigation getNavigation(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getPathNavigation(entity);
    }

    /**
     * Returns the {@link MoveController} for this entity
     */
    @NMS
    @Nonnull
    public static MoveController getMoveController(@Nonnull final Mob entity) {
        return JeffLib.getNMSHandler().getMoveControl(entity);
    }
}
