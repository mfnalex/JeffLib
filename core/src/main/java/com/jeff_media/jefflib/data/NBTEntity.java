package com.jeff_media.jefflib.data;

/**
 * Represents an {@link org.bukkit.entity.Entity} with changeable NBT data. You <b>must</b> call {@link #save()} after changing any data for the changes to take effect
 */
public interface NBTEntity {

    /**
     * Applies the changes made to this entity
     */
    void save();

}
