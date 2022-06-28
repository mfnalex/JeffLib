package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import javax.annotation.Nonnull;

import javax.annotation.Nullable;
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

    @Nonnull
    public static String getNBT(@Nonnull final PersistentDataHolder holder, @Nonnull final String key, @Nonnull final String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(holder, key)) {
            return getNBT(holder, key);
        }
        return defaultValue;
    }

    @Nonnull
    public static String getNBT(@Nonnull final ItemStack item, @Nonnull final String key, @Nonnull final String defaultValue) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(key, "DefaultValue must not be null");
        if (hasNBT(item, key)) {
            return getNBT(item, key);
        }
        return defaultValue;
    }

    @Nullable
    public static
    String getNBT(@Nonnull final PersistentDataHolder holder, @Nonnull final String key) {
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

    @Nullable
    public static
    String getNBT(@Nonnull final ItemStack item, @Nonnull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return null;
        return getNBT(item.getItemMeta(), key);
    }

    public static void addNBT(@Nonnull final PersistentDataHolder holder, @Nonnull final String key, @Nonnull final String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        final NamespacedKey namespacedKey = new NamespacedKey(JeffLib.getPlugin(), key);
        pdc.set(namespacedKey, PersistentDataType.STRING, value);
    }

    public static void addNBT(@Nonnull final ItemStack item, @Nonnull final String key, @Nonnull final String value) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        Objects.requireNonNull(key, "Value must not be null");
        final ItemMeta meta = item.hasItemMeta() ? item.getItemMeta() : Bukkit.getItemFactory().getItemMeta(item.getType());
        addNBT(meta, key, value);
        item.setItemMeta(meta);
    }

    public static boolean hasNBT(@Nonnull final ItemStack item, @Nonnull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return false;
        final ItemMeta meta = item.getItemMeta();
        final PersistentDataContainer pdc = meta.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    public static boolean hasNBT(@Nonnull final PersistentDataHolder holder, @Nonnull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        return pdc.has(new NamespacedKey(JeffLib.getPlugin(), key), PersistentDataType.STRING);
    }

    public static void removeNBT(@Nonnull final ItemStack item, @Nonnull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        if (!item.hasItemMeta()) return;
        final ItemMeta meta = item.getItemMeta();
        removeNBT(meta, key);
        item.setItemMeta(meta);
    }

    public static void removeNBT(@Nonnull final PersistentDataHolder holder, @Nonnull final String key) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        Objects.requireNonNull(key, "Key must not be null");
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        pdc.remove(new NamespacedKey(JeffLib.getPlugin(), key));
    }

    public static HashMap<String, String> getAllValues(@Nonnull final PersistentDataHolder holder) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(holder, "PersistentDataHolder must not be null");
        final HashMap<String, String> map = new HashMap<>();
        final PersistentDataContainer pdc = holder.getPersistentDataContainer();
        for (final NamespacedKey key : pdc.getKeys()) {
            map.put(key.toString(), pdc.get(key, PersistentDataType.STRING));
        }
        return map;
    }

    public static HashMap<String, String> getAllValues(@Nonnull final ItemStack item) {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
        Objects.requireNonNull(item, "ItemStack must not be null");
        final HashMap<String, String> map = new HashMap<>();
        if (!item.hasItemMeta()) return map;
        return getAllValues(item.getItemMeta());
    }

}
