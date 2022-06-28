package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.Contract;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * PersistentDataContainer related methods.
 * <p>
 * All methods accepting Strings as Keys need JeffLib to be initialized first!
 */
@UtilityClass
@SuppressWarnings("unused")
public class PDCUtils {

    private static final Plugin plugin = JeffLib.getPlugin();
    private static final Map<String,NamespacedKey> KEYS = new HashMap<>();

    /**
     * Sets a value in the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param value  Value
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    public static <T, Z> void set(@Nonnull final PersistentDataHolder holder,
                                  @Nonnull final String key,
                                  @Nonnull final PersistentDataType<T, Z> type,
                                  @Nonnull final Z value) {
        set(holder, getKey(key), type, value);
    }

    /**
     * Sets a value in the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param value  Value
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    public static <T, Z> void set(@Nonnull final PersistentDataHolder holder,
                                  @Nonnull final NamespacedKey key,
                                  @Nonnull final PersistentDataType<T, Z> type,
                                  @Nonnull final Z value) {
        holder.getPersistentDataContainer().set(key, type, value);
    }

    /**
     * Creates a NamespacedKey or returns a cached one. <b>JeffLib has to be initialized first.</b>
     *
     * @param key Key name
     * @return NamespacedKey
     */
    public static NamespacedKey getKey(final String key) {
        return KEYS.computeIfAbsent(key, __ -> new NamespacedKey(JeffLib.getPlugin(), key));
    }

    /**
     * Gets a value from the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    @Nullable
    public static <T, Z> Z get(@Nonnull final PersistentDataHolder holder,
                               @Nonnull final String key,
                               @Nonnull final PersistentDataType<T, Z> type) {
        return get(holder, getKey(key), type);
    }

    /**
     * Gets a value from the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    @Nullable
    public static <T, Z> Z get(@Nonnull final PersistentDataHolder holder,
                               @Nonnull final NamespacedKey key,
                               @Nonnull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().get(key, type);
    }

    /**
     * Sets a value in the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param value  Value
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    public static <T, Z> void set(@Nonnull final ItemStack holder,
                                  @Nonnull final String key,
                                  @Nonnull final PersistentDataType<T, Z> type,
                                  @Nonnull final Z value) {
        set(holder, getKey(key), type, value);
    }

    /**
     * Sets a value in the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param value  Value
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    public static <T, Z> void set(@Nonnull final ItemStack holder,
                                  @Nonnull final NamespacedKey key,
                                  @Nonnull final PersistentDataType<T, Z> type,
                                  @Nonnull final Z value) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        set(meta, key, type, value);
        holder.setItemMeta(meta);
    }

    /**
     * Gets a value from the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    @Nullable
    public static <T, Z> Z get(@Nonnull final ItemStack holder,
                               @Nonnull final String key,
                               @Nonnull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return get(holder.getItemMeta(), getKey(key), type);
    }

    /**
     * Gets a value from the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     */
    @Nullable
    public static <T, Z> Z get(@Nonnull final ItemStack holder,
                               @Nonnull final NamespacedKey key,
                               @Nonnull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return get(holder.getItemMeta(), key, type);
    }

    /**
     * Gets a value from the holder's PDC or the default value when the key is not set
     *
     * @param holder       Holder
     * @param key          Key name
     * @param type         Data type
     * @param defaultValue Default value
     * @param <T>          Primitive data type
     * @param <Z>          Complex data type
     */
    @Nullable
    @Contract("_, _, _, !null -> !null")
    public static <T, Z> Z getOrDefault(@Nonnull final ItemStack holder,
                                        @Nonnull final String key,
                                        @Nonnull final PersistentDataType<T, Z> type,
                                        final Z defaultValue) {
        Objects.requireNonNull(holder.getItemMeta());
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    /**
     * Gets a value from the holder's PDC or the default value when the key is not set
     *
     * @param holder       Holder
     * @param key          Key name
     * @param type         Data type
     * @param defaultValue Default value
     * @param <T>          Primitive data type
     * @param <Z>          Complex data type
     */
    @Nullable
    @Contract("_, _, _, !null -> !null")
    public static <T, Z> Z getOrDefault(@Nonnull final PersistentDataHolder holder,
                                        @Nonnull final String key,
                                        @Nonnull final PersistentDataType<T, Z> type,
                                        final Z defaultValue) {
        return getOrDefault(holder, getKey(key), type, defaultValue);
    }

    /**
     * Gets a value from the holder's PDC or the default value when the key is not set
     *
     * @param holder       Holder
     * @param key          NamespacedKey
     * @param type         Data type
     * @param defaultValue Default value
     * @param <T>          Primitive data type
     * @param <Z>          Complex data type
     */
    @Nullable
    @Contract("_, _, _, !null -> !null")
    public static <T, Z> Z getOrDefault(@Nonnull final PersistentDataHolder holder,
                                        @Nonnull final NamespacedKey key,
                                        @Nonnull final PersistentDataType<T, Z> type,
                                        final Z defaultValue) {
        return holder.getPersistentDataContainer().getOrDefault(key, type, defaultValue);
    }

    /**
     * Gets a value from the holder's PDC or the default value when the key is not set
     *
     * @param holder       Holder
     * @param key          NamespacedKey
     * @param type         Data type
     * @param defaultValue Default value
     * @param <T>          Primitive data type
     * @param <Z>          Complex data type
     */
    @Nullable
    @Contract("_, _, _, !null -> !null")
    public static <T, Z> Z getOrDefault(@Nonnull final ItemStack holder,
                                        @Nonnull final NamespacedKey key,
                                        @Nonnull final PersistentDataType<T, Z> type,
                                        final Z defaultValue) {
        Objects.requireNonNull(holder.getItemMeta());
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    /**
     * Removes a key from the holder's PDC
     * @param holder    Holder
     * @param key       Key name
     */
    public static void remove(@Nonnull final PersistentDataHolder holder,
                              @Nonnull final String key) {
        remove(holder, getKey(key));
    }

    /**
     * Removes a key from the holder's PDC
     * @param holder    Holder
     * @param key       NamespacedKey
     */
    public static void remove(@Nonnull final PersistentDataHolder holder,
                              @Nonnull final NamespacedKey key) {
        holder.getPersistentDataContainer().remove(key);
    }

    /**
     * Checks whether the holder's PDC contains a key
     * @param holder    Holder
     * @param key       Key name
     * @param type      Data type
     * @param <T>       Primitive data type
     * @param <Z>       Complex data type
     * @return          True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@Nonnull final PersistentDataHolder holder,
                                     @Nonnull final String key,
                                     @Nonnull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(getKey(key), type);
    }

    /**
     * Removes a key from the holder's PDC
     * @param holder    Holder
     * @param key       NamespacedKey
     */
    public static void remove(@Nonnull final ItemStack holder,
                              @Nonnull final NamespacedKey key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, key);
        holder.setItemMeta(meta);
    }

    /**
     * Removes a key from the holder's PDC
     * @param holder    Holder
     * @param key       Key name
     */
    public static void remove(@Nonnull final ItemStack holder,
                              @Nonnull final String key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, getKey(key));
        holder.setItemMeta(meta);
    }

    /**
     * Checks whether the holder's PDC contains a key
     * @param holder    Holder
     * @param key       Key name
     * @param type      Data type
     * @param <T>       Primitive data type
     * @param <Z>       Complex data type
     * @return          True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@Nonnull final ItemStack holder,
                              @Nonnull final String key,
                              @Nonnull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), getKey(key), type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     * @param holder    Holder
     * @param key       NamespacedKey
     * @param type      Data type
     * @param <T>       Primitive data type
     * @param <Z>       Complex data type
     * @return          True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@Nonnull final PersistentDataHolder holder,
                                     @Nonnull final NamespacedKey key,
                                     @Nonnull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(key, type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     * @param holder    Holder
     * @param key       NamespacedKey
     * @param type      Data type
     * @param <T>       Primitive data type
     * @param <Z>       Complex data type
     * @return          True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@Nonnull final ItemStack holder,
                              @Nonnull final NamespacedKey key,
                              @Nonnull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), key, type);
    }

    /**
     * Returns a Set of all the NamespacedKeys the holder's PDC contains
     * @param holder    Holder
     * @return          Set of all NamespacedKeys the holder's PDC contains
     */
    @Nonnull
    public Set<NamespacedKey> getKeys(@Nonnull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return getKeys(holder.getItemMeta());
    }

    /**
     * Returns a Set of all the NamespacedKeys the holder's PDC contains
     * @param holder    Holder
     * @return          Set of all NamespacedKeys the holder's PDC contains
     */
    @Nonnull
    public static Set<NamespacedKey> getKeys(@Nonnull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    /**
     * Checks whether the holder's PDC is empty
     * @param holder    Holder
     * @return          True when the holder's PDC is empty, otherwise false
     */
    public static boolean isEmpty(@Nonnull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return isEmpty(holder.getItemMeta());
    }

    /**
     * Checks whether the holder's PDC is empty
     * @param holder    Holder
     * @return          True when the holder's PDC is empty, otherwise false
     */
    public static boolean isEmpty(@Nonnull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

}
