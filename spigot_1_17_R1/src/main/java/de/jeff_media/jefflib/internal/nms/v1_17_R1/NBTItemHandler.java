package de.jeff_media.jefflib.internal.nms.v1_17_R1;

import de.jeff_media.jefflib.internal.nms.NBTItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;

public class NBTItemHandler extends NBTItem {

    private ItemStack nmsItem;
    private NBTTagCompound compound;

    public NBTItemHandler(org.bukkit.inventory.ItemStack item){
        if(item==null) return;
        this.nmsItem = CraftItemStack.asNMSCopy(item);
        this.compound = nmsItem.getOrCreateTag();
    }

    @Override
    public void setString(String key, String value) {
        compound.setString(key,value);
    }

    @Override
    public void setBoolean(String key, boolean value) {
        compound.setBoolean(key,value);
    }

    @Override
    public void setInt(String key, int value) {
        compound.setInt(key,value);
    }

    @Override
    public void setLong(String key, long value) {
        compound.setLong(key,value);
    }

    @Override
    public void setDouble(String key, double value) {
        compound.setDouble(key,value);
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
        return compound.hasKey(key);
    }

    @Override
    public org.bukkit.inventory.ItemStack getBukkitItem() {
        nmsItem.save(compound);
        return CraftItemStack.asBukkitCopy(nmsItem);
    }

    @Override
    public void removeKey(String key) {
        compound.remove(key);
    }
}
