package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Objects;

public class NBTAPI {

    public static @NotNull String getNBT(@NotNull PersistentDataHolder holder, @NotNull String key, @NotNull String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(holder, key)) {
            return getNBT(holder, key);
        }
        return defaultValue;
    }

    public static @NotNull String getNBT(@NotNull ItemStack item, @NotNull String key, @NotNull String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(item, key)) {
            return getNBT(item, key);
        }
        return defaultValue;
    }

    public static @Nullable String getNBT(@NotNull PersistentDataHolder holder, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        PersistentDataContainer pdc = holder.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(JeffLib.getPlugin(), key);
        if (pdc.has(namespacedKey, PersistentDataType.STRING)) {
            return pdc.get(namespacedKey, PersistentDataType.STRING);
        }
        return null;
    }

    public static @Nullable
    String getNBT(@NotNull ItemStack item, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return null;
        return getNBT(item.getItemMeta(), key);
    }

    public static void addNBT(@NotNull PersistentDataHolder holder, @NotNull String key, @NotNull String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        PersistentDataContainer pdc = holder.getPersistentDataContainer();
        NamespacedKey namespacedKey = new NamespacedKey(JeffLib.getPlugin(), key);
        pdc.set(namespacedKey, PersistentDataType.STRING, value);
    }

    public static void addNBT(@NotNull ItemStack item, @NotNull String key, @NotNull String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        addNBT(meta, key, value);
        item.setItemMeta(meta);
    }

    public static boolean hasNBT(@NotNull ItemStack item, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    public static boolean hasNBT(@NotNull PersistentDataHolder holder, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        PersistentDataContainer pdc = holder.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void removeNBT(@NotNull ItemStack item, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        removeNBT(meta, key);
        item.setItemMeta(meta);
    }

    public static void removeNBT(@NotNull PersistentDataHolder holder, @NotNull String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        PersistentDataContainer pdc = holder.getPersistentDataContainer();
        pdc.remove(new NamespacedKey(JeffLib.getPlugin(), key));
    }

    public static HashMap<String, String> getAllValues(@NotNull PersistentDataHolder holder) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        HashMap<String, String> map = new HashMap<>();
        PersistentDataContainer pdc = holder.getPersistentDataContainer();
        for (NamespacedKey key : pdc.getKeys()) {
            map.put(key.toString(), pdc.get(key, PersistentDataType.STRING));
        }
        return map;
    }

    public static HashMap<String, String> getAllValues(@NotNull ItemStack item) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        HashMap<String, String> map = new HashMap<>();
        if (!item.hasItemMeta()) return map;
        return getAllValues(item.getItemMeta());
    }

}
