package de.jeff_media.jefflib;

import org.bukkit.Chunk;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Set;

/**
 * PersistentDataContainer for blocks!
 */
public class CustomBlockData implements PersistentDataContainer {

    private static final Plugin plugin = JeffLib.getPlugin();
    private final PersistentDataContainer pdc;
    private final Block block;
    private final Chunk chunk;
    private final NamespacedKey key;

    public CustomBlockData(Block block) {
        this.pdc = getPDC(block);
        this.block = block;
        this.chunk = block.getChunk();
        this.key = getKey(block);
    }

    static {
        Objects.requireNonNull(JeffLib.getPlugin(), "JeffLib hasn't been initialized.");
    }

    private PersistentDataContainer getPDC(Block block) {
        Chunk chunk = block.getChunk();
        PersistentDataContainer chunkPDC = chunk.getPersistentDataContainer();
        if(chunkPDC.has(key, PersistentDataType.TAG_CONTAINER)) {
            return chunkPDC.get(key, PersistentDataType.TAG_CONTAINER);
        }
        PersistentDataContainer blockPDC = chunkPDC.getAdapterContext().newPersistentDataContainer();
        chunkPDC.set(key, PersistentDataType.TAG_CONTAINER, blockPDC);
        return blockPDC;
    }

    private void save() {
        chunk.getPersistentDataContainer().set(key, PersistentDataType.TAG_CONTAINER, pdc);
    }

    private static NamespacedKey getKey(Block block) {
        int x = block.getX() & 0x000F;
        int y = block.getY() & 0x00FF;
        int z = block.getZ() & 0x000F;

        return new NamespacedKey(plugin, String.format("%d/%d/%d", x, y, z));
    }

    @Override
    public <T, Z> void set(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType, @NotNull Z z) {
        pdc.set(namespacedKey, persistentDataType, z);
        save();
    }

    @Override
    public <T, Z> boolean has(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.has(namespacedKey, persistentDataType);
    }

    @Nullable
    @Override
    public <T, Z> Z get(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType) {
        return pdc.get(namespacedKey, persistentDataType);
    }

    @NotNull
    @Override
    public <T, Z> Z getOrDefault(@NotNull NamespacedKey namespacedKey, @NotNull PersistentDataType<T, Z> persistentDataType, @NotNull Z z) {
        return pdc.getOrDefault(namespacedKey, persistentDataType, z);
    }

    @NotNull
    @Override
    public Set<NamespacedKey> getKeys() {
        return pdc.getKeys();
    }

    @Override
    public void remove(@NotNull NamespacedKey namespacedKey) {
        pdc.remove(namespacedKey);
        save();
    }

    @Override
    public boolean isEmpty() {
        return pdc.isEmpty();
    }

    @NotNull
    @Override
    public PersistentDataAdapterContext getAdapterContext() {
        return pdc.getAdapterContext();
    }
}
