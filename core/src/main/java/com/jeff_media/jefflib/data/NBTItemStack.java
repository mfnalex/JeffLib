package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.inventory.ItemStack;

/**
 * Represents an {@link ItemStack} with adjustable NBT data
 */
public interface NBTItemStack extends NBTDataHolder{

    /**
     * Creates a new {@link NBTItemStack} from an {@link ItemStack}
     */
    static NBTItemStack of(ItemStack itemStack) {
        return JeffLib.getNMSHandler().getNBTItemStack(itemStack);
    }

    ItemStack toItemStack();

}
