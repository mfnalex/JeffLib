package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * Entity related methods
 */
@UtilityClass
public final class EntityUtils {

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
    @NotNull
    public static <T extends Entity> Collection<T> getEntities(@NotNull final Class<T> entityClass) {
        final Collection<T> list = new ArrayList<>();
        for(final World world : Bukkit.getWorlds()) {
            list.addAll(world.getEntitiesByClass(entityClass));
        }
        return list;
    }

    /**
     * Returns a collection of all entities from all worlds
     */
    @NotNull
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
}
