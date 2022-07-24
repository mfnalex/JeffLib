package com.jeff_media.jefflib.internal.nms.v1_18_R2;

import com.jeff_media.jefflib.data.NBTDataHolder;
import com.jeff_media.jefflib.data.NBTItemStack;
import net.minecraft.nbt.CompoundTag;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class HatchedNBTItem extends NBTItemStack {

    private final CompoundTag tag;
    private final net.minecraft.world.item.ItemStack item;

    public HatchedNBTItem(ItemStack item) {
        this.item = CraftItemStack.asNMSCopy(item);
        this.tag = this.item.getOrCreateTag();
    }

    @Override
    public void setString(String key, String value) {
        tag.putString(key, value);
    }

    @Override
    public byte getByte(String key) {
        return tag.getByte(key);
    }

    @Override
    public short getShort(String key) {
        return tag.getShort(key);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        tag.putBoolean(key, value);
    }

    @Override
    public void setByte(String key, byte value) {
        tag.putByte(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        tag.putShort(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        tag.putInt(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        tag.putLong(key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        tag.putFloat(key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        tag.putDouble(key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        tag.putByteArray(key, value);
    }

    @Override
    public String getString(String key) {
        return tag.getString(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return tag.getBoolean(key);
    }

    @Override
    public int getInt(String key) {
        return tag.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return tag.getLong(key);
    }

    @Override
    public float getFloat(String key) {
        return tag.getFloat(key);
    }

    @Override
    public double getDouble(String key) {
        return tag.getDouble(key);
    }

    @Override
    public byte[] getByteArray(String key) {
        return tag.getByteArray(key);
    }

    @Override
    public void setIntArray(String key, int[] value) {
        tag.putIntArray(key, value);
    }

    @Override
    public int[] getIntArray(String key) {
        return tag.getIntArray(key);
    }

    @Override
    public void setLongArray(String key, long[] value) {
        tag.putLongArray(key, value);
    }

    @Override
    public long[] getLongArray(String key) {
        return tag.getLongArray(key);
    }

    @Override
    public boolean hasKey(String key) {
        return tag.contains(key);
    }

    @Override
    public boolean hasKey(String key, TagType tagType) {
        return tag.contains(key, tagType.getId());
    }

    @Override
    public void removeKey(String key) {
        tag.remove(key);
    }

    @Override
    public ItemStack toItemStack() {
        item.save(tag);
        return CraftItemStack.asBukkitCopy(item);
    }

    @Override
    public Set<String> getKeys() {
        return tag.getAllKeys();
    }
}
