package com.jeff_media.jefflib.internal.nms.v1_16_R3;

import com.jeff_media.jefflib.data.NBTDataHolder;
import com.jeff_media.jefflib.data.NBTItemStack;
import net.minecraft.server.v1_16_R3.NBTTagCompound;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class HatchedNBTItem extends NBTItemStack {

    private final NBTTagCompound tag;
    private final net.minecraft.server.v1_16_R3.ItemStack item;

    public HatchedNBTItem(ItemStack item) {
        this.item = CraftItemStack.asNMSCopy(item);
        this.tag = this.item.getOrCreateTag();
    }

    @Override
    public void setString(String key, String value) {
        tag.setString(key, value);
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
        tag.setBoolean(key, value);
    }

    @Override
    public void setByte(String key, byte value) {
        tag.setByte(key, value);
    }

    @Override
    public void setShort(String key, short value) {
        tag.setShort(key, value);
    }

    @Override
    public void setInt(String key, int value) {
        tag.setInt(key, value);
    }

    @Override
    public void setLong(String key, long value) {
        tag.setLong(key, value);
    }

    @Override
    public void setFloat(String key, float value) {
        tag.setFloat(key, value);
    }

    @Override
    public void setDouble(String key, double value) {
        tag.setDouble(key, value);
    }

    @Override
    public void setByteArray(String key, byte[] value) {
        tag.setByteArray(key, value);
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
        tag.setIntArray(key, value);
    }

    @Override
    public int[] getIntArray(String key) {
        return tag.getIntArray(key);
    }

    @Override
    public void setLongArray(String key, long[] value) {
        tag.a(key, value);
    }

    @Override
    public long[] getLongArray(String key) {
        return tag.getLongArray(key);
    }

    @Override
    public boolean hasKey(String key) {
        return tag.hasKey(key);
    }

    @Override
    public boolean hasKey(String key, TagType tagType) {
        return tag.hasKeyOfType(key, tagType.getId());
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
        return tag.getKeys();
    }
}

