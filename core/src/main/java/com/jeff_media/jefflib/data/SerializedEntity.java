package com.jeff_media.jefflib.data;

import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

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

    public EntityType getEntityType() {
        return entityType;
    }

    public String getNbtData() {
        return nbtData;
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        Map<String,Object> map = new HashMap<>();
        map.put("entityType",entityType.name());
        map.put("nbtData",nbtData);
        return map;
    }

    public static SerializedEntity deserialize(Map<String, Object> map) {
        return new SerializedEntity(map);
    }
}
