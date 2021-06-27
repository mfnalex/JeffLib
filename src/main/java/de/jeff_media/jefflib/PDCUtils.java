package de.jeff_media.jefflib;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PDCUtils {
    public static final PersistentDataType<Byte, Byte> BYTE = PersistentDataType.BYTE;
    public static final PersistentDataType<byte[], byte[]> BYTES = PersistentDataType.BYTE_ARRAY;
    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    public static final PersistentDataType<Integer, Integer> INT = PersistentDataType.INTEGER;
    public static final PersistentDataType<int[], int[]> INTS = PersistentDataType.INTEGER_ARRAY;
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    public static final PersistentDataType<PersistentDataContainer, PersistentDataContainer> CONTAINER = PersistentDataType.TAG_CONTAINER;
    public static final PersistentDataType<PersistentDataContainer[], PersistentDataContainer[]> CONTAINERS = PersistentDataType.TAG_CONTAINER_ARRAY;

    public static NamespacedKey getKey(Plugin plugin, String key) {
        return new NamespacedKey(plugin, key);
    }

    public static <T, Z> void set(@NotNull PersistentDataHolder holder,
                                  @NotNull String key,
                                  @NotNull PersistentDataType<T, Z> type,
                                  @NotNull Z value,
                                  @NotNull Plugin plugin) {
        holder.getPersistentDataContainer().set(getKey(plugin, key), type, value);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull PersistentDataHolder holder,
                               @NotNull String key,
                               @NotNull PersistentDataType<T, Z> type,
                               @NotNull Plugin plugin) {
        return holder.getPersistentDataContainer().get(getKey(plugin, key), type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull PersistentDataHolder holder,
                                        @NotNull String key,
                                        @NotNull PersistentDataType<T, Z> type,
                                        @NotNull Z defaultValue,
                                        @NotNull Plugin plugin) {
        return holder.getPersistentDataContainer().getOrDefault(getKey(plugin, key), type, defaultValue);
    }

    public static <T, Z> void set(@NotNull ItemStack holder,
                                  @NotNull String key,
                                  @NotNull PersistentDataType<T, Z> type,
                                  @NotNull Z value,
                                  @NotNull Plugin plugin) {
        ItemMeta meta = holder.getItemMeta();
        set(meta, key, type, value, plugin);
        holder.setItemMeta(meta);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull ItemStack holder,
                               @NotNull String key,
                               @NotNull PersistentDataType<T, Z> type,
                               @NotNull Plugin plugin) {
        return get(holder.getItemMeta(), key, type, plugin);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull ItemStack holder,
                                        @NotNull String key,
                                        @NotNull PersistentDataType<T, Z> type,
                                        @NotNull Z defaultValue,
                                        @NotNull Plugin plugin) {
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue, plugin);
    }

    public void remove(@NotNull PersistentDataHolder holder,
                       @NotNull String key,
                       @NotNull Plugin plugin) {
        holder.getPersistentDataContainer().remove(getKey(plugin, key));
    }

    public <T, Z> boolean has(@NotNull PersistentDataHolder holder,
                              @NotNull String key,
                              @NotNull PersistentDataType<T, Z> type,
                              @NotNull Plugin plugin) {
        return holder.getPersistentDataContainer().has(getKey(plugin, key), type);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    public boolean isEmpty(@NotNull PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

    public void remove(@NotNull ItemStack holder,
                       @NotNull String key,
                       @NotNull Plugin plugin) {
        ItemMeta meta = holder.getItemMeta();
        remove(meta, key, plugin);
        holder.setItemMeta(meta);
    }

    public <T, Z> boolean has(@NotNull ItemStack holder,
                              @NotNull String key,
                              @NotNull PersistentDataType<T, Z> type,
                              @NotNull Plugin plugin) {
        return has(holder.getItemMeta(), key, type, plugin);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull ItemStack holder) {
        return getKeys(holder.getItemMeta());
    }

    public boolean isEmpty(@NotNull ItemStack holder) {
        return isEmpty(holder.getItemMeta());
    }

}
