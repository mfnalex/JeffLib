package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.JeffLibNotInitializedException;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang.Validate;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

/**
 * PersistentDataContainer related methods.
 *
 * ALL methods accepting Strings as Keys need JeffLib to be initialized first!
 */
@UtilityClass
public class PDCUtils {

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
        set(holder, getKey(key), type, value);
    }

    public static <T, Z> void set(@NotNull final PersistentDataHolder holder,
                                  @NotNull final NamespacedKey key,
                                  @NotNull final PersistentDataType<T, Z> type,
                                  @NotNull final Z value) {
        holder.getPersistentDataContainer().set(key, type, value);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final PersistentDataHolder holder,
                               @NotNull final String key,
                               @NotNull final PersistentDataType<T, Z> type) {
        return get(holder, getKey(key), type);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final PersistentDataHolder holder,
                               @NotNull final NamespacedKey key,
                               @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().get(key, type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final PersistentDataHolder holder,
                                        @NotNull final String key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        return getOrDefault(holder, getKey(key), type, defaultValue);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final PersistentDataHolder holder,
                                        @NotNull final NamespacedKey key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        return holder.getPersistentDataContainer().getOrDefault(key, type, defaultValue);
    }

    public static <T, Z> void set(@NotNull final ItemStack holder,
                                  @NotNull final String key,
                                  @NotNull final PersistentDataType<T, Z> type,
                                  @NotNull final Z value) {
        set(holder, getKey(key), type, value);
    }

    public static <T, Z> void set(@NotNull final ItemStack holder,
                                  @NotNull final NamespacedKey key,
                                  @NotNull final PersistentDataType<T, Z> type,
                                  @NotNull final Z value) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        set(meta, key, type, value);
        holder.setItemMeta(meta);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final ItemStack holder,
                               @NotNull final String key,
                               @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return get(holder.getItemMeta(), getKey(key), type);
    }

    @Nullable
    public static <T, Z> Z get(@NotNull final ItemStack holder,
                               @NotNull final NamespacedKey key,
                               @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return get(holder.getItemMeta(), key, type);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final ItemStack holder,
                                        @NotNull final String key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        Objects.requireNonNull(holder.getItemMeta());
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    @NotNull
    public static <T, Z> Z getOrDefault(@NotNull final ItemStack holder,
                                        @NotNull final NamespacedKey key,
                                        @NotNull final PersistentDataType<T, Z> type,
                                        @NotNull final Z defaultValue) {
        Objects.requireNonNull(holder.getItemMeta());
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    public static void remove(@NotNull final PersistentDataHolder holder,
                              @NotNull final String key) {
        remove(holder, getKey(key));
    }

    public static void remove(@NotNull final PersistentDataHolder holder,
                              @NotNull final NamespacedKey key) {
        holder.getPersistentDataContainer().remove(key);
    }

    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder,
                                     @NotNull final String key,
                                     @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(getKey(key), type);
    }

    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder,
                                     @NotNull final NamespacedKey key,
                                     @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(key, type);
    }

    @NotNull
    public static Set<NamespacedKey> getKeys(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    public static boolean isEmpty(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

    public static void remove(@NotNull final ItemStack holder,
                              @NotNull final NamespacedKey key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, key);
        holder.setItemMeta(meta);
    }

    public static void remove(@NotNull final ItemStack holder,
                              @NotNull final String key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, getKey(key));
    }

    public <T, Z> boolean has(@NotNull final ItemStack holder,
                              @NotNull final String key,
                              @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), getKey(key), type);
    }

    public <T, Z> boolean has(@NotNull final ItemStack holder,
                              @NotNull final NamespacedKey key,
                              @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), key, type);
    }

    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return getKeys(holder.getItemMeta());
    }

    public static boolean isEmpty(@NotNull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return isEmpty(holder.getItemMeta());
    }

}
