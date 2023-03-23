package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a serialized entity that can be respawned later, keeping ALL NBT data
 *
 * @nms
 */
@NMS
public class SerializedEntity implements ConfigurationSerializable {

    private final EntityType entityType;
    private final String nbtData;

    public SerializedEntity(EntityType entityType, String nbtData) {
        this.entityType = entityType;
        this.nbtData = nbtData;
    }

    public SerializedEntity(Map<String, Object> map) {
        this.entityType = EntityType.valueOf((String) map.get("entityType"));
        this.nbtData = (String) map.get("nbtData");
    }

    /**
     * Creates a new SerializedEntity from the given entity
     */
    public static SerializedEntity of(Entity entity) {
        return JeffLib.getNMSHandler().serialize(entity);
    }

    public static SerializedEntity deserialize(Map<String, Object> map) {
        return new SerializedEntity(map);
    }

    /**
     * Returns the original entity's {@link EntityType}
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * Returns the original entity's NBT data as String
     */
    public String getNbtData() {
        return nbtData;
    }

    /**
     * Spawns the entity at the given location
     *
     * @return The spawned entity
     */
    public Entity spawn(@NotNull org.bukkit.Location location) {
        Entity entity = location.getWorld().spawnEntity(location, entityType);
        JeffLib.getNMSHandler().applyNbt(entity, nbtData);
        return entity;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new HashMap<>();
        map.put("entityType", entityType.name());
        map.put("nbtData", nbtData);
        return map;
    }
}
