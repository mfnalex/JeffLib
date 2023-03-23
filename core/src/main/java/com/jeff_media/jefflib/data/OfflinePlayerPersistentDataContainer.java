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

package com.jeff_media.jefflib.data;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.ProfileUtils;
import com.jeff_media.jefflib.ReflUtils;
import com.jeff_media.jefflib.internal.annotations.Internal;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.annotations.Paper;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import org.jetbrains.annotations.NotNull;
import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a {@link PersistentDataContainer} from an {@link org.bukkit.OfflinePlayer}. <b>Important:</b> When changing values, you have to call {@link #save()} or {@link #saveAsync()} afterwards.
 *
 * @nms
 */
@NMS
public class OfflinePlayerPersistentDataContainer implements PersistentDataContainer {

    private final PersistentDataContainer craftPersistentDataContainer;
    private final File file;
    private final Object compoundTag;

    /**
     * @internal
     * @internal For internal use only
     */
    @Internal
    public OfflinePlayerPersistentDataContainer(@NotNull PersistentDataContainer craftPersistentDataContainer, @NotNull File file, @NotNull Object compoundTag) {
        this.craftPersistentDataContainer = craftPersistentDataContainer;
        this.file = file;
        this.compoundTag = compoundTag;
    }

    /**
     * Returns an OfflinePlayer's {@link PersistentDataContainer}.&nbsp;<b>Important: </b>When doing changes to the PDC, you must call {@link OfflinePlayerPersistentDataContainer#save()} or {@link OfflinePlayerPersistentDataContainer#saveAsync()} to save the changes.
     * The player's .dat file must already exist, i.e. it doesn't work for players who have never joined before.
     *
     * @nms
     */
    @NotNull
    @NMS
    public static CompletableFuture<OfflinePlayerPersistentDataContainer> of(UUID uuid) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return JeffLib.getNMSHandler().getPDCFromDatFile(ProfileUtils.getPlayerDataFile(uuid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Returns an OfflinePlayer's {@link PersistentDataContainer}.&nbsp;<b>Important: </b>When doing changes to the PDC, you must call {@link OfflinePlayerPersistentDataContainer#save()} or {@link OfflinePlayerPersistentDataContainer#saveAsync()} to save the changes.
     * The player's .dat file must already exist, i.e. it doesn't work for players who have never joined before.
     *
     * @nms
     */
    @NotNull
    @NMS
    public static CompletableFuture<OfflinePlayerPersistentDataContainer> of(OfflinePlayer player) {
        return of(player.getUniqueId());
    }

    /**
     * For internal use only
     *
     * @internal
     * @internal For internal use only
     */
    @Deprecated
    @Internal
    public Object getCraftPersistentDataContainer() {
        return craftPersistentDataContainer;
    }

    /**
     * For internal use only
     *
     * @internal
     * @internal For internal use only
     */
    @Deprecated
    @Internal
    public Object getCompoundTag() {
        return compoundTag;
    }

    /**
     * Returns the player's data .dat file
     */
    public File getFile() {
        return file;
    }

    /**
     * Saves the data to the player's file. This will overwrite any changes made to the original file that happened after
     * creating this instance.
     */
    public void save() {
        try {
            JeffLib.getNMSHandler().updatePdcInDatFile(this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Saves the data to the player's file. This will overwrite any changes made to the original file that happened after
     * creating this instance.
     */
    public CompletableFuture<Void> saveAsync() {
        return CompletableFuture.runAsync(this::save);
    }

    @Override
    public <T, Z> void set(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType, @NotNull Z z) {
        craftPersistentDataContainer.set(namespacedKey, persistentDataType, z);
    }

    @Override
    public <T, Z> boolean has(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType) {
        return craftPersistentDataContainer.has(namespacedKey, persistentDataType);
    }

    public boolean has(@NotNull NamespacedKey namespacedKey) {
        return craftPersistentDataContainer.getKeys().contains(namespacedKey);
    }

    /**
     * @paper
     */
    //@Override
    @Paper
    public @NotNull byte[] serializeToBytes() throws IOException {
        try {
            return (byte[]) ReflUtils.getMethod(craftPersistentDataContainer.getClass(), "serializeToBytes").invoke(craftPersistentDataContainer);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
        //return craftPersistentDataContainer.serializeToBytes();
    }

    /**
     * @paper
     */
    //@Override
    @Paper
    public void readFromBytes(@NotNull byte[] bytes, boolean b) throws IOException {
        try {
            ReflUtils.getMethod(craftPersistentDataContainer.getClass(), "readFromBytes", byte[].class, boolean.class).invoke(craftPersistentDataContainer, bytes, b);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
        //craftPersistentDataContainer.readFromBytes(bytes, b);
    }

    @Override
    public <T, Z> Z get(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType) {
        return craftPersistentDataContainer.get(namespacedKey, persistentDataType);
    }

    @Override
    @NotNull
    public <T, Z> Z getOrDefault(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType, @NotNull Z z) {
        return craftPersistentDataContainer.getOrDefault(namespacedKey, persistentDataType, z);
    }

    @Override
    @NotNull
    public Set<NamespacedKey> getKeys() {
        return craftPersistentDataContainer.getKeys();
    }

    @Override
    public void remove(@NotNull NamespacedKey namespacedKey) {
        craftPersistentDataContainer.remove(namespacedKey);
    }

    @Override
    public boolean isEmpty() {
        return craftPersistentDataContainer.isEmpty();
    }

    @Override
    @NotNull
    public PersistentDataAdapterContext getAdapterContext() {
        return craftPersistentDataContainer.getAdapterContext();
    }
}
