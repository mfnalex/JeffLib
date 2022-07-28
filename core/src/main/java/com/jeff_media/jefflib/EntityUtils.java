package com.jeff_media.jefflib;

import com.jeff_media.jefflib.ai.CustomGoal;
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
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;

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
    public static @Nullable Player getClosestOtherPlayer(final @Nonnull Player player) {
        return player.getWorld().getPlayers().stream()
                .filter(other -> other != player)
                .min(new Comparators.EntityByDistanceComparator(player))
                .orElse(null);
    }

    /**
     * Gets the generic movement speed of an entity
     */
    public static double getMovementSpeed(final @Nonnull LivingEntity entity) {
        AttributeInstance attribute = entity.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED);
        if(attribute != null) {
            return attribute.getValue();
        }
        return DEFAULT_GENERIC_MOVEMENT_SPEED;
    }

    /**
     * Gets the closest player in this world, or null if there is no player
     */
    public static @Nullable Player getClosestPlayer(final @Nonnull Location location) {
        return location.getWorld().getPlayers().stream()
                .min(new Comparators.EntityByDistanceComparator(location))
                .orElse(null);
    }

    /**
     * Gets the closest player in this world, or null if there is no player
     */
    public static @Nullable Player getClosestPlayer(final @Nonnull Entity entity) {
        return getClosestPlayer(entity.getLocation());
    }

    /**
     * Gets the closest player in this world, or null if there is no player
     */
    public static @Nullable Player getClosestPlayer(final @Nonnull Block block) {
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
        for(final World world : Bukkit.getWorlds()) {
            list.addAll(world.getEntitiesByClass(entityClass));
        }
        return list;
    }

    /**
     * Returns a collection of all entities from all worlds
     */
    @Nonnull
    public static Collection<Entity> getAllEntities() {
        final Collection<Entity> list = new ArrayList<>();
        for(final World world : Bukkit.getWorlds()) {
            list.addAll(world.getEntities());
        }
        return list;
    }

    /**
     * Gets an entity by its Entity ID
     */
    @Nullable
    public static Entity getEntityById(final int id) {
        for(final Entity entity : getAllEntities()) {
            if(entity.getEntityId()==id) return entity;
        }
        return null;
    }

    /**
     * Lets an Entity walk to a certain location
     */
    public static boolean walkTo(final @Nonnull LivingEntity entity, final double x, final double y, final double z, final double speed) {
        return JeffLib.getNMSHandler().moveTo(entity, x, y, z, speed);
    }

    public static boolean walkTo(final @Nonnull LivingEntity entity, final @Nonnull Location location, final double speed) {
        return walkTo(entity, location.getX(), location.getY(), location.getZ(), speed);
    }

    public static boolean isPathfinderMob(Entity entity) {
        return JeffLib.getNMSHandler().isPathfinderMob(entity);
    }

    /**
     * Adds a {@link PathfinderGoal} to an entity
     * @param entity Entity to add the goal to
     * @param goal Goal to add
     * @param priority Priority of the goal
     * @return true if the goal was added successfully, false if the given entity is not a pathfinding mob (e.g. Bats)
     * @throws UnsupportedOperationException If the given PathfinderGoal is not supposed
     */
    public static boolean addPathfinderGoal(LivingEntity entity, PathfinderGoal goal, int priority) {
        return JeffLib.getNMSHandler().addGoal(entity, goal, priority);
    }

    /**
     * Returns the {@link PathNavigation} for this entity, or null if it's not a pathfinding mob (e.g. Bats)
     * @return PathNavigation or null or not a pathfinding mob (e.g. Bats)
     */
    @NMS
    @Nullable public static PathNavigation getNavigation(@Nonnull LivingEntity entity) {
        return JeffLib.getNMSHandler().getPathNavigation(entity);
    }
}
