package com.jeff_media.jefflib.internal.nms.v1_19_R1;

import com.jeff_media.jefflib.data.NBTItemStack;
import net.minecraft.world.item.ItemStack;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;

public class HatchedNBTItemStack extends HatchedNBTDataHolder implements NBTItemStack {

    private final ItemStack nmsItemStack;

    public HatchedNBTItemStack(ItemStack nmsItemStack) {
        super(nmsItemStack.getOrCreateTag());
        this.nmsItemStack = nmsItemStack;
    }

    public HatchedNBTItemStack(org.bukkit.inventory.ItemStack bukkitItemStack) {
        super();
        nmsItemStack = CraftItemStack.asNMSCopy(bukkitItemStack);
        super.tag = nmsItemStack.getOrCreateTag();

    }

    @Override
    public org.bukkit.inventory.ItemStack toItemStack() {
        nmsItemStack.save(tag);
        return CraftItemStack.asBukkitCopy(nmsItemStack);
    }
}
