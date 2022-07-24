package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Represents an object that can hold NBT data
 */
public interface NBTDataHolder {

    public enum TagType {
        BYTE(1),
        SHORT(2),
        INT(3),
        LONG(4),
        FLOAT(5),
        DOUBLE(6),
        BYTE_ARRAY(7),
        STRING(8),
        LIST(9),
        COMPOUND(10),
        INT_ARRAY(11),
        LONG_ARRAY(12);

        @Getter private final int id;

        TagType(int id) {
            this.id = id;
        }
    }

    /*
    Byte
     */
    public abstract void setByte(String key, byte value);
    public abstract byte getByte(String key);
    public abstract void setByteArray(String key, byte[] value);
    public abstract byte[] getByteArray(String key);

    /*
    Boolean
     */
    public abstract void setBoolean(String key, boolean value);
    public abstract boolean getBoolean(String key);

    /*
    Short
     */
    public abstract void setShort(String key, short value);
    public abstract short getShort(String key);

    /*
    Integer
     */
    public abstract void setInt(String key, int value);
    public abstract int getInt(String key);
    public abstract void setIntArray(String key, int[] value);
    public abstract int[] getIntArray(String key);

    /*
    Long
     */
    public abstract void setLong(String key, long value);
    public abstract long getLong(String key);
    public abstract void setLongArray(String key, long[] value);
    public abstract long[] getLongArray(String key);

    /*
    Float
     */
    public abstract void setFloat(String key, float value);
    public abstract float getFloat(String key);

    /*
    Double
     */
    public abstract void setDouble(String key, double value);
    public abstract double getDouble(String key);

    /*
    String
     */
    public abstract void setString(String key, String value);
    public abstract String getString(String key);

    public abstract boolean hasKey(String key);

    public abstract boolean hasKey(String key, TagType tagType);

    public abstract void removeKey(String key);

    public abstract Set<String> getKeys();

}
