/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.util.HashMap;
import java.util.Objects;

/**
 * Provides methods to store and retrieve Strings from PersistentDataHolders or ItemStacks
 *
 * @deprecated Use {@link PDCUtils} instead
 */
@Deprecated
@UtilityClass
public class NBTAPI {

    @NotNull
    public static String getNBT(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(holder, key)) {
            return getNBT(holder, key);
        }
        return defaultValue;
    }

    public static boolean hasNBT(@NotNull final PersistentDataHolder holder, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    @Nullable
    public static String getNBT(@NotNull final PersistentDataHolder holder, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        final NamespacedKey namespacedKey = new NamespacedKey(JeffLib.getPlugin(), key);
        if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
            return pdc.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    @NotNull
    public static String getNBT(@NotNull final ItemStack item, @NotNull final String key, @NotNull final String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(item, key)) {
            return getNBT(item, key);
        }
        return defaultValue;
    }

    public static boolean hasNBT(@NotNull final ItemStack item, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return false;
        final ItemMeta meta = item.getItemMeta();
        final PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    @Nullable
    public static String getNBT(@NotNull final ItemStack item, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return null;
        return getNBT(item.getItemMeta(), key);
    }

    public static void addNBT(@NotNull final ItemStack item, @NotNull final String key, @NotNull final String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        final ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        addNBT(meta, key, value);
        item.setItemMeta(meta);
    }

    public static void addNBT(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        final NamespacedKey namespacedKey = new NamespacedKey(JeffLib.getPlugin(), key);
        pdc.set(namespacedKey, PersistentDataType.STRING, value);
    }

    public static void removeNBT(@NotNull final ItemStack item, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return;
        final ItemMeta meta = item.getItemMeta();
        removeNBT(meta, key);
        item.setItemMeta(meta);
    }

    public static void removeNBT(@NotNull final PersistentDataHolder holder, @NotNull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        pdc.remove(new NamespacedKey(JeffLib.getPlugin(), key));
    }

    public static HashMap<String, String> getAllValues(@NotNull final ItemStack item) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        final HashMap<String, String> map = new HashMap<>();
        if (!item.hasItemMeta()) return map;
        return getAllValues(item.getItemMeta());
    }

    public static HashMap<String, String> getAllValues(@NotNull final PersistentDataHolder holder) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        final HashMap<String, String> map = new HashMap<>();
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        for (final NamespacedKey key : pdc.getKeys()) {
            map.put(key.toString(), pdc.get(key, PersistentDataType.STRING));
        }
        return map;
    }

}
