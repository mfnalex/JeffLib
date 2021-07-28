package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;

/**
 * Provides a builder for ItemStacks
 */
public class ItemBuilder {
    private final ItemStack item;
    private final ItemMeta meta;

    public ItemBuilder(Material mat, int amount) {
        item = new ItemStack(mat, amount);
        meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(mat);
    }

    public ItemBuilder(Material mat) {
        this(mat, 1);
    }

    /**
     * Returns the built ItemStack
     * @return ItemStack
     */
    public ItemStack build() {
        item.setItemMeta(meta);
        return item;
    }

    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder setName(@Nullable String name) {
        meta.setDisplayName(name);
        return this;
    }

    public ItemBuilder setLore(@Nullable List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    public ItemBuilder setLore(@Nullable String... lore) {
        if(lore==null) {
            meta.setLore(null);
        } else {
            meta.setLore(Arrays.asList(lore));
        }
        return this;
    }

    public ItemBuilder setCustomModelData(@Nullable Integer data) {
        meta.setCustomModelData(data);
        return this;
    }


}
