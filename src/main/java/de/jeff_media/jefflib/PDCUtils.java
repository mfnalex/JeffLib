package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
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

/**
 * PersistentDataContainer related methods.
 * @deprecated Draft & unfinished at the moment
 */
@Deprecated
public class PDCUtils {
    public static final PersistentDataType<Byte, Byte> BYTE = PersistentDataType.BYTE;
    public static final PersistentDataType<byte[], byte[]> BYTES = PersistentDataType.BYTE_ARRAY;
    public static final PersistentDataType<String, String> STRING = PersistentDataType.STRING;
    public static final PersistentDataType<Integer, Integer> INT = PersistentDataType.INTEGER;
    public static final PersistentDataType<int[], int[]> INTS = PersistentDataType.INTEGER_ARRAY;
    public static final PersistentDataType<Double, Double> DOUBLE = PersistentDataType.DOUBLE;
    public static final PersistentDataType<PersistentDataContainer, PersistentDataContainer> CONTAINER = PersistentDataType.TAG_CONTAINER;
    public static final PersistentDataType<PersistentDataContainer[], PersistentDataContainer[]> CONTAINERS = PersistentDataType.TAG_CONTAINER_ARRAY;

    private static final Plugin plugin = JeffLib.getPlugin();

    /**
     * Creates a NamespacedKey. <b>JeffLib has to be initialized first.</b>
     * @param key Key name
     * @return NamespacedKey
     */
    public static NamespacedKey getKey(String key) {
        if(plugin == null) {
            throw new JeffLibNotInitializedException();
        }
        return new NamespacedKey(JeffLib.getPlugin(), key);
    }

    /**
     * Sets a value in the holder's PDC
     * @param holder Holder
     * @param key Key name
     * @param type Data type
     * @param value Value
     * @param <T>
     * @param <Z>
     */
    public static <T, Z> void set(@NotNull PersistentDataHolder holder,
                                  @NotNull String key,
                                  @NotNull PersistentDataType<T, Z> type,
                                  @NotNull Z value) {
        holder.getPersistentDataContainer().set(getKey(key), type, value);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull PersistentDataHolder holder,
                               @NotNull String key,
                               @NotNull PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().get(getKey(key), type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull PersistentDataHolder holder,
                                        @NotNull String key,
                                        @NotNull PersistentDataType<T, Z> type,
                                        @NotNull Z defaultValue) {
        return holder.getPersistentDataContainer().getOrDefault(getKey(key), type, defaultValue);
    }

    public static <T, Z> void set(@NotNull ItemStack holder,
                                  @NotNull String key,
                                  @NotNull PersistentDataType<T, Z> type,
                                  @NotNull Z value) {
        ItemMeta meta = holder.getItemMeta();
        set(meta, key, type, value);
        holder.setItemMeta(meta);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull ItemStack holder,
                               @NotNull String key,
                               @NotNull PersistentDataType<T, Z> type) {
        return get(holder.getItemMeta(), key, type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull ItemStack holder,
                                        @NotNull String key,
                                        @NotNull PersistentDataType<T, Z> type,
                                        @NotNull Z defaultValue) {
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    public void remove(@NotNull PersistentDataHolder holder,
                       @NotNull String key) {
        holder.getPersistentDataContainer().remove(getKey(key));
    }

    public <T, Z> boolean has(@NotNull PersistentDataHolder holder,
                              @NotNull String key,
                              @NotNull PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(getKey(key), type);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    public boolean isEmpty(@NotNull PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

    public void remove(@NotNull ItemStack holder,
                       @NotNull String key) {
        ItemMeta meta = holder.getItemMeta();
        remove(meta, key);
        holder.setItemMeta(meta);
    }

    public <T, Z> boolean has(@NotNull ItemStack holder,
                              @NotNull String key,
                              @NotNull PersistentDataType<T, Z> type) {
        return has(holder.getItemMeta(), key, type);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull ItemStack holder) {
        return getKeys(holder.getItemMeta());
    }

    public boolean isEmpty(@NotNull ItemStack holder) {
        return isEmpty(holder.getItemMeta());
    }

}
