package de.jeff_media.jefflib;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.util.BoundingBox;

import java.util.Collection;
import java.util.function.Predicate;

/**
 * Provides methods related to entities
 */
public class MobUtils {

    /**
     * Gets all entities inside {@param min} and {@param max}
     * @param min first corner
     * @param max second corner
     * @return Collection of all entities inside {@param min} and {@param max}
     */
    public static Collection<Entity> getEntities(Block min, Block max) {
        World world = min.getWorld();
        if (!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world!");
        }
        BoundingBox box = BoundingBox.of(min, max);
        return world.getNearbyEntities(box);
    }

    /**
     * Gets all entities inside {@param min} and {@param max} that extend {@param entityClass}
     * @param min first corner
     * @param max second corner
     * @param entityClass class the entities have to extend
     * @return Collection of all entities inside {@param min} and {@param max} that extend {@param entityClass}
     */
    public static Collection<? extends Entity> getEntities(Block min, Block max, Class<? extends Entity> entityClass) {
        World world = min.getWorld();
        if (!world.equals(max.getWorld())) {
            throw new IllegalArgumentException("Both locations must share the same world!");
        }
        BoundingBox box = BoundingBox.of(min, max);
        Collection<? extends Entity> entities = world.getEntitiesByClass(entityClass);
        entities.removeIf((Predicate<Entity>) entity -> !box.contains(entity.getLocation().toVector()));
        return entities;
    }

    public static Collection<? extends Entity> getEntities(Block min, Block max, EntityType entityType) {
        return getEntities(min, max, entityType.getEntityClass());
    }
}
