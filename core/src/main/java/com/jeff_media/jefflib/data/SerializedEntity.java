/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
