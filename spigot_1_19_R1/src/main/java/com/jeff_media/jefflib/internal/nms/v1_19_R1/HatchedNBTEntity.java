package com.jeff_media.jefflib.internal.nms.v1_19_R1;

import com.jeff_media.jefflib.data.NBTEntity;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;

public class HatchedNBTEntity extends HatchedNBTDataHolder implements NBTEntity {

    private final net.minecraft.world.entity.Entity entity;

    public HatchedNBTEntity(Entity entity) {
        CompoundTag tag = new CompoundTag();
        CraftEntity craftEntity = (CraftEntity) entity;
        this.entity = craftEntity.getHandle();
        this.entity.save(tag);
        super.tag = tag;
    }


    @Override
    public void save() {
        this.entity.load(tag);
    }
}
