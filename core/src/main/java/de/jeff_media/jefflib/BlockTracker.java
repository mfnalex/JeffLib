package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import javax.annotation.Nonnull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

/**
 * Tracks player placed blocks.
 * <p>
 * Uses the chunk's PersistentDataContainer to store information about which blocks have been placed
 * by the player. You can track all block types or only certain ones.
 * <p>
 * <b>Important: Tracking blocks requires registering the listener using {@link JeffLib#registerBlockTracker()}</b>
 */
@UtilityClass
public final class BlockTracker {

    private static final Plugin plugin = JeffLib.getPlugin();
    private static final NamespacedKey PLAYER_PLACED_TAG = new NamespacedKey(plugin, "playerplaced");
    private static final Collection<Material> TRACKED_TYPES = new HashSet<>();

    /**
     * Adds a new material to the block tracker
     *
     * @param type material to track
     */
    public static void addTrackedBlockType(final Material type) {
        TRACKED_TYPES.add(type);
    }

    /**
     * Adds new materials to the block tracker
     *
     * @param types materials to track
     */
    public static void addTrackedBlockTypes(final Collection<Material> types) {
        TRACKED_TYPES.addAll(types);
    }

    /**
     * Gets a collection containing all tracked materials
     *
     * @return Collection containing all tracked materials
     */
    public static Collection<Material> getTrackedBlockTypes() {
        return TRACKED_TYPES;
    }

    /**
     * Tracks all materials
     */
    public static void trackAllBlockTypes() {
        addTrackedBlockTypes(Arrays.asList(Material.values()));
    }

    /**
     * Clears the list of tracked materials
     */
    public static void clearTrackedBlockTypes() {
        TRACKED_TYPES.clear();
    }

    /**
     * Checks whether a given material is already one of the tracked block types.
     *
     * @param type Material to check
     * @return true when this material is already tracked, otherwise false
     */
    public static boolean isTrackedBlockType(final Material type) {
        return TRACKED_TYPES.contains(type);
    }

    /**
     * Removes all given block types from the list of tracked block types
     *
     * @param types Collection of Materials to stop tracking
     */
    public static void removeTrackedBlockTypes(final Collection<Material> types) {
        TRACKED_TYPES.removeAll(types);
    }

    /**
     * Checks whether a given block has been placed by a player
     *
     * @param block Block to check
     * @return true when the block was player-placed and tracked, otherwise false
     */
    public static boolean isPlayerPlacedBlock(final Block block) {
        final PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        return playerPlacedPDC.has(getKey(block), PersistentDataType.BYTE);
    }

    /**
     * Gets a collection of all blocks that have been placed by players inside a chunk
     *
     * @param chunk Chunk to check
     * @return Collection of all blocks inside the chunk that have been placed by players
     */
    @Nonnull
    public static Collection<Block> getPlayerPlacedBlocks(final Chunk chunk) {
        final Collection<Block> blocks = new HashSet<>();
        final PersistentDataContainer pdc = getPlayerPlacedPDC(chunk);
        for (final NamespacedKey key : pdc.getKeys()) {
            if (!key.getNamespace().equals(PLAYER_PLACED_TAG.getNamespace())) continue;
            final String[] parts = key.getKey().split("/");
            final int x = Integer.parseInt(parts[0]);
            final int y = Integer.parseInt(parts[1]);
            final int z = Integer.parseInt(parts[2]);
            blocks.add(chunk.getBlock(x, y, z));
        }
        return blocks;
    }

    /**
     * Manually sets whether a player placed this block
     *
     * @param block        Block
     * @param playerPlaced Whether the block was player placed
     */
    public static void setPlayerPlacedBlock(final Block block, final boolean playerPlaced) {
        final PersistentDataContainer pdc = block.getChunk().getPersistentDataContainer();
        final PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        final NamespacedKey key = getKey(block);
        if (playerPlaced) {
            playerPlacedPDC.set(key, PersistentDataType.BYTE, (byte) 1);
        } else {
            playerPlacedPDC.remove(key);
        }
        pdc.set(PLAYER_PLACED_TAG, PersistentDataType.TAG_CONTAINER, playerPlacedPDC);
    }

    private static PersistentDataContainer getPlayerPlacedPDC(final PersistentDataHolder chunk) {
        final PersistentDataContainer pdc = chunk.getPersistentDataContainer();
        return pdc.getOrDefault(PLAYER_PLACED_TAG, PersistentDataType.TAG_CONTAINER, pdc.getAdapterContext().newPersistentDataContainer());
    }

    private static NamespacedKey getKey(final Block block) {
        final int x = block.getX() & 0x000F;
        final int y = block.getY();
        final int z = block.getZ() & 0x000F;

        //noinspection HardcodedFileSeparator
        return new NamespacedKey(plugin, String.format("%d/%d/%d", x, y, z));
    }

}
