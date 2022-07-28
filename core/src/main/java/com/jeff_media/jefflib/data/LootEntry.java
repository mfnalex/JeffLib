package com.jeff_media.jefflib.data;

import lombok.Data;
import lombok.Getter;
import org.bukkit.inventory.ItemStack;

@Data
public class LootEntry {

    @Getter
    private ItemStack itemStack;
    @Getter
    private double chance;

}
