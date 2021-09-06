package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import lombok.experimental.UtilityClass;
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
 *
 * @deprecated Draft, unfinished at the moment
 */
@Deprecated
@UtilityClass
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
     *
     * @param key Key name
     * @return NamespacedKey
     */
    public static NamespacedKey getKey(final String key) {
        if (plugin == null) {
            throw new JeffLibNotInitializedException();
        }
        return new NamespacedKey(JeffLib.getPlugin(), key);
    }

    /**
     * Sets a value in the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param value  Value
     * @param <T>    primitive data type
     * @param <Z>    complex data type
     */
    public static <T, Z> void set(@NotNull final PersistentDataHolder holder,
                                  @NotNull final String key,
                                  @NotNull final PersistentDataType<T, Z> type,
                                  @NotNull final Z value) {
        holder.getPersistentDataContainer().set(getKey(key), type, value);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final PersistentDataHolder holder,
                               @NotNull final String key,
                               @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().get(getKey(key), type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final PersistentDataHolder holder,
                                        @NotNull final String key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        return holder.getPersistentDataContainer().getOrDefault(getKey(key), type, defaultValue);
    }

    public static <T, Z> void set(@NotNull final ItemStack holder,
                                  @NotNull final String key,
                                  @NotNull final PersistentDataType<T, Z> type,
                                  @NotNull final Z value) {
        final ItemMeta meta = holder.getItemMeta();
        set(meta, key, type, value);
        holder.setItemMeta(meta);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final ItemStack holder,
                               @NotNull final String key,
                               @NotNull final PersistentDataType<T, Z> type) {
        return get(holder.getItemMeta(), key, type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final ItemStack holder,
                                        @NotNull final String key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    public static void remove(@NotNull final PersistentDataHolder holder,
                              @NotNull final String key) {
        holder.getPersistentDataContainer().remove(getKey(key));
    }

    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder,
                                     @NotNull final String key,
                                     @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(getKey(key), type);
    }

    @NotNull
    public static Set<NamespacedKey> getKeys(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    public static boolean isEmpty(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

    public static void remove(@NotNull final ItemStack holder,
                              @NotNull final String key) {
        final ItemMeta meta = holder.getItemMeta();
        remove(meta, key);
        holder.setItemMeta(meta);
    }

    public <T, Z> boolean has(@NotNull final ItemStack holder,
                              @NotNull final String key,
                              @NotNull final PersistentDataType<T, Z> type) {
        return has(holder.getItemMeta(), key, type);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull final ItemStack holder) {
        return getKeys(holder.getItemMeta());
    }

    public static boolean isEmpty(@NotNull final ItemStack holder) {
        return isEmpty(holder.getItemMeta());
    }

}
