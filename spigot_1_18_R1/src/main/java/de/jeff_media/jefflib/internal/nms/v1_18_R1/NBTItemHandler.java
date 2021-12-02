package de.jeff_media.jefflib.internal.nms.v1_18_R1;

import de.jeff_media.jefflib.internal.nms.NBTItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;

public class NBTItemHandler extends NBTItem {

    private ItemStack nmsItem;
    private CompoundTag compound;

    public NBTItemHandler(){}

    public NBTItemHandler(org.bukkit.inventory.ItemStack item){
        if(item == null) return;
        this.nmsItem = CraftItemStack.asNMSCopy(item);
        this.compound = nmsItem.getOrCreateTag();
    }

    @Override
    public void setString(String key, String value) {
        compound.putString(key,value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        compound.putBoolean(key,value);
    }

    @Override
    public void setInt(String key, int value) {
        compound.putInt(key,value);
    }

    @Override
    public void setLong(String key, long value) {
        compound.putLong(key,value);
    }

    @Override
    public void setDouble(String key, double value) {

    }

    @Override
    public String getString(String key) {
        return compound.getString(key);
    }

    @Override
    public boolean getBoolean(String key) {
        return compound.getBoolean(key);
    }

    @Override
    public int getInt(String key) {
        return compound.getInt(key);
    }

    @Override
    public long getLong(String key) {
        return compound.getLong(key);
    }

    @Override
    public double getDouble(String key) {
        return compound.getDouble(key);
    }

    @Override
    public boolean hasKey(String key) {
        return compound.contains(key);
    }

    @Override
    public org.bukkit.inventory.ItemStack getItem() {
        nmsItem.save(compound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public void removeKey(String key) {
        compound.remove(key);
    }
}
