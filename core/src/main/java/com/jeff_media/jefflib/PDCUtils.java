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

import com.jeff_media.jefflib.data.OfflinePlayerPersistentDataContainer;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.cherokee.Validate;
import lombok.experimental.UtilityClass;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.Contract;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * PersistentDataContainer related methods.
 * <p>
 * All methods accepting Strings as Keys need JeffLib to be initialized first!
 */
@UtilityClass
@SuppressWarnings("unused")
public class PDCUtils {

    /**
     * An array of all primitive {@link PersistentDataType}s builtin to Bukkit.
     */
    public static final PersistentDataType<?, ?>[] PRIMITIVE_DATA_TYPES = new PersistentDataType<?, ?>[] {
            PersistentDataType.BYTE,
            PersistentDataType.SHORT,
            PersistentDataType.INTEGER,
            PersistentDataType.LONG,
            PersistentDataType.FLOAT,
            PersistentDataType.DOUBLE,
            PersistentDataType.STRING,
            PersistentDataType.BYTE_ARRAY,
            PersistentDataType.INTEGER_ARRAY,
            PersistentDataType.LONG_ARRAY,
            PersistentDataType.TAG_CONTAINER_ARRAY,
            PersistentDataType.TAG_CONTAINER};
    private static final Map<String, NamespacedKey> KEYS = new HashMap<>();

    /**
     * Generates a random NamespacedKey
     */
    public static NamespacedKey getRandomKey() {
        return new NamespacedKey(JeffLib.getPlugin(), UUID.randomUUID().toString());
    }

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
    public static <T, Z> void set(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type, @NotNull final Z value) {
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
    public static <T, Z> void set(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type, @NotNull final Z value) {
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
    public static <T, Z> Z get(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type) {
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
    public static <T, Z> Z get(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type) {
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
    public static <T, Z> void set(@NotNull final ItemStack holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type, @NotNull final Z value) {
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
    public static <T, Z> void set(@NotNull final ItemStack holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type, @NotNull final Z value) {
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
    public static <T, Z> Z get(@NotNull final ItemStack holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type) {
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
    public static <T, Z> Z get(@NotNull final ItemStack holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type) {
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
    public static <T, Z> Z getOrDefault(@NotNull final ItemStack holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type, final Z defaultValue) {
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
    public static <T, Z> Z getOrDefault(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type, final Z defaultValue) {
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
    public static <T, Z> Z getOrDefault(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type, final Z defaultValue) {
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
    public static <T, Z> Z getOrDefault(@NotNull final ItemStack holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type, final Z defaultValue) {
        Objects.requireNonNull(holder.getItemMeta());
        return getOrDefault(holder.getItemMeta(), key, type, defaultValue);
    }

    /**
     * Removes a key from the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     */
    public static void remove(@NotNull final PersistentDataHolder holder, @NotNull final String key) {
        remove(holder, getKey(key));
    }

    /**
     * Removes a key from the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     */
    public static void remove(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key) {
        holder.getPersistentDataContainer().remove(key);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(getKey(key), type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    Key name
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder, @NotNull final String key) {
        return holder.getPersistentDataContainer().getKeys().contains(getKey(key));
    }

    /**
     * Removes a key from the holder's PDC
     *
     * @param holder Holder
     * @param key    NamespacedKey
     */
    public static void remove(@NotNull final ItemStack holder, @NotNull final NamespacedKey key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, key);
        holder.setItemMeta(meta);
    }

    /**
     * Removes a key from the holder's PDC
     *
     * @param holder Holder
     * @param key    Key name
     */
    public static void remove(@NotNull final ItemStack holder, @NotNull final String key) {
        final ItemMeta meta = holder.getItemMeta();
        Objects.requireNonNull(meta);
        remove(meta, getKey(key));
        holder.setItemMeta(meta);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    Key name
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@NotNull final ItemStack holder, @NotNull final String key, @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), getKey(key), type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    Key name
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@NotNull final ItemStack holder, @NotNull final String key) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), getKey(key));
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type) {
        return holder.getPersistentDataContainer().has(key, type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public static <T, Z> boolean has(@NotNull final PersistentDataHolder holder, @NotNull final NamespacedKey key) {
        return holder.getPersistentDataContainer().getKeys().contains(key);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param type   Data type
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@NotNull final ItemStack holder, @NotNull final NamespacedKey key, @NotNull final PersistentDataType<T, Z> type) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), key, type);
    }

    /**
     * Checks whether the holder's PDC contains a key
     *
     * @param holder Holder
     * @param key    NamespacedKey
     * @param <T>    Primitive data type
     * @param <Z>    Complex data type
     * @return True when the PDC contains the key, otherwise false
     */
    public <T, Z> boolean has(@NotNull final ItemStack holder, @NotNull final NamespacedKey key) {
        Objects.requireNonNull(holder.getItemMeta());
        return has(holder.getItemMeta(), key);
    }

    /**
     * Returns a Set of all the NamespacedKeys the holder's PDC contains
     *
     * @param holder Holder
     * @return Set of all NamespacedKeys the holder's PDC contains
     */
    @NotNull
    public Set<NamespacedKey> getKeys(@NotNull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return getKeys(holder.getItemMeta());
    }

    /**
     * Returns a Set of all the NamespacedKeys the holder's PDC contains
     *
     * @param holder Holder
     * @return Set of all NamespacedKeys the holder's PDC contains
     */
    @NotNull
    public static Set<NamespacedKey> getKeys(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().getKeys();
    }

    /**
     * Checks whether the holder's PDC is empty
     *
     * @param holder Holder
     * @return True when the holder's PDC is empty, otherwise false
     */
    public static boolean isEmpty(@NotNull final ItemStack holder) {
        Objects.requireNonNull(holder.getItemMeta());
        return isEmpty(holder.getItemMeta());
    }

    /**
     * Checks whether the holder's PDC is empty
     *
     * @param holder Holder
     * @return True when the holder's PDC is empty, otherwise false
     */
    public static boolean isEmpty(@NotNull final PersistentDataHolder holder) {
        return holder.getPersistentDataContainer().isEmpty();
    }

    /**
     * Copies all the data from the source PDC to the destination PDC. If the destination PDC already contains a key, the key will be overwritten.
     */
    @SuppressWarnings("unchecked")
    public static void copy(@NotNull final PersistentDataContainer source, @NotNull final PersistentDataContainer target) {
        for (final NamespacedKey key : source.getKeys()) {
            final PersistentDataType<Object, Object> type = (PersistentDataType<Object, Object>) getDataType(source, key);
            Validate.notNull(type, "Could not find data type for key " + key);
            final Object value = source.get(key, type);
            if (value != null) {
                target.set(key, type, value);
            }
        }
    }

    /**
     * Gets the proper primitive {@link PersistentDataType} for the given {@link NamespacedKey} in the given {@link PersistentDataContainer}
     *
     * @return The primitive PersistentDataType for the given key, or null if the key doesn't exist
     */
    public static PersistentDataType<?, ?> getDataType(@NotNull final PersistentDataContainer pdc, @NotNull final NamespacedKey key) {
        for (PersistentDataType<?, ?> dataType : PRIMITIVE_DATA_TYPES) {
            if (pdc.has(key, dataType)) return dataType;
        }
        return null;
    }

    /**
     * Turns a PersistentDataContainer into String
     *
     * @nms
     */
    @NMS
    @NotNull
    public static String serialize(@NotNull final PersistentDataContainer pdc) {
        return JeffLib.getNMSHandler().serializePdc(pdc);
    }

    /**
     * Loads a String from {@link PDCUtils#serialize(PersistentDataContainer)} into a PersistentDataContainer, overwriting already existing keys of the same name
     *
     * @throws IOException When the String cannot be deserialized
     */
    public static void deserialize(@NotNull final String serializedPdc, @NotNull final PersistentDataContainer target) throws IOException {
        try {
            JeffLib.getNMSHandler().deserializePdc(serializedPdc, target);
        } catch (Exception e) {
            throw new IOException("Could not deserialize PDC", e);
        }
    }


    /**
     * Returns an OfflinePlayer's {@link PersistentDataContainer}.&nbsp;<b>Important: </b>When doing changes to the PDC, you must call {@link OfflinePlayerPersistentDataContainer#save()} or {@link OfflinePlayerPersistentDataContainer#saveAsync()} to save the changes.
     * The player's .dat file must already exist, i.e. it doesn't work for players who have never joined before.
     *
     * @nms
     */
    @NotNull
    @NMS
    public static CompletableFuture<OfflinePlayerPersistentDataContainer> getOfflinePlayerPersistentDataContainer(UUID uuid) {
        return OfflinePlayerPersistentDataContainer.of(uuid);
    }

    /**
     * Returns an OfflinePlayer's {@link PersistentDataContainer}.&nbsp;<b>Important: </b>When doing changes to the PDC, you must call {@link OfflinePlayerPersistentDataContainer#save()} or {@link OfflinePlayerPersistentDataContainer#saveAsync()} to save the changes.
     * The player's .dat file must already exist, i.e. it doesn't work for players who have never joined before.
     *
     * @nms
     */
    @NotNull
    @NMS
    public static CompletableFuture<OfflinePlayerPersistentDataContainer> getOfflinePlayerPersistentDataContainer(OfflinePlayer player) {
        return OfflinePlayerPersistentDataContainer.of(player);
    }

}
