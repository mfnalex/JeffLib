package com.jeff_media.jefflib.internal.nms;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public interface AbstractNMSTranslationKeyProvider {
    String getItemTranslationKey(Material mat);

    String getBlockTranslationKey(Material mat);

    String getTranslationKey(Block block);

    String getTranslationKey(EntityType entityType);

    String getTranslationKey(ItemStack itemStack);
}
