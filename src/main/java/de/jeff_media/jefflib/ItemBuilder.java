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

    /**
     * Creates a new ItemBuilder with the given material and amount
     * @param mat Material
     * @param amount Amount
     */
    public ItemBuilder(Material mat, int amount) {
        item = new ItemStack(mat, amount);
        meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(mat);
    }

    /**
     * Creates a new ItemBuilder with the given material and an amount of 1
     * @param mat
     */
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

    /**
     * Sets the amount
     * @param amount
     * @return ItemBuilder instance
     */
    public ItemBuilder setAmount(int amount) {
        item.setAmount(amount);
        return this;
    }

    /**
     * Sets the item's display name
     * @param name Item's display name (or null)
     * @return ItemBuilder instance
     */
    public ItemBuilder setName(@Nullable String name) {
        meta.setDisplayName(name);
        return this;
    }

    /**
     * Sets the item's lore
     * @param lore Lore (or null)
     * @return ItemBuilder instance
     */
    public ItemBuilder setLore(@Nullable List<String> lore) {
        meta.setLore(lore);
        return this;
    }

    /**
     * Sets the item's lore
     * @param lore Lore (or null)
     * @return ItemBuilder instance
     */
    public ItemBuilder setLore(@Nullable String... lore) {
        if(lore==null) {
            meta.setLore(null);
        } else {
            meta.setLore(Arrays.asList(lore));
        }
        return this;
    }

    /**
     * Sets the item's Custom Model Data
     * @param data Custom Model Data as int value, or null
     * @return ItemBuilder instance
     */
    public ItemBuilder setCustomModelData(@Nullable Integer data) {
        meta.setCustomModelData(data);
        return this;
    }


}
