package de.jeff_media.jefflib;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Tracks player placed blocks.
 *
 * Uses the chunk's PersistentDataContainer to store information about which blocks have been placed
 * by the player. You can track all block types or only certain ones.
 */
public class BlockTracker {

    private static final Plugin plugin = JeffLib.getPlugin();
    private static final NamespacedKey PLAYER_PLACED_TAG = new NamespacedKey(plugin, "playerplaced");
    private static final Collection<Material> TRACKED_TYPES = new HashSet<>();

    /**
     * Adds a new material to the block tracker
     * @param type material to track
     */
    public static void addTrackedBlockType(Material type) {
        TRACKED_TYPES.add(type);
    }

    /**
     * Adds new materials to the block tracker
     * @param types materials to track
     */
    public static void addTrackedBlockTypes(Collection<Material> types) {
        TRACKED_TYPES.addAll(types);
    }

    /**
     * Gets a collection containing all tracked materials
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
     * @param type Material to check
     * @return true when this material is already tracked, otherwise false
     */
    public static boolean isTrackedBlockType(Material type) {
        return TRACKED_TYPES.contains(type);
    }

    /**
     * Removes all given block types from the list of tracked block types
     * @param types Collection of Materials to stop tracking
     */
    public static void removeTrackedBlockTypes(Collection<Material> types) {
        TRACKED_TYPES.removeAll(types);
    }

    /**
     * Checks whether a given block has been placed by a player
     * @param block Block to check
     * @return true when the block was player-placed and tracked, otherwise false
     */
    public static boolean isPlayerPlacedBlock(Block block) {
        PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        return playerPlacedPDC.has(getKey(block), PersistentDataType.BYTE);
    }

    /**
     * Gets a collection of all blocks that have been placed by players inside a chunk
     * @param chunk Chunk to check
     * @return Collection of all blocks inside the chunk that have been placed by players
     */
    public static @NotNull Collection<Block> getPlayerPlacedBlocks(Chunk chunk) {
        Set<Block> blocks = new HashSet<>();
        PersistentDataContainer pdc = getPlayerPlacedPDC(chunk);
        for(NamespacedKey key : pdc.getKeys()) {
            if(!key.getNamespace().equals(PLAYER_PLACED_TAG.getNamespace())) continue;
            String[] parts = key.getKey().split("/");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int z = Integer.parseInt(parts[2]);
            blocks.add(chunk.getBlock(x,y,z));
        }
        return blocks;
    }

    /**
     * Manually sets whether a player placed this block
     * @param block Block
     * @param playerPlaced Whether the block was player placed
     */
    public static void setPlayerPlacedBlock(Block block, boolean playerPlaced) {
        PersistentDataContainer pdc = block.getChunk().getPersistentDataContainer();
        PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        NamespacedKey key = getKey(block);
        if (playerPlaced) {
            playerPlacedPDC.set(key, PersistentDataType.BYTE, (byte) 1);
        } else {
            playerPlacedPDC.remove(key);
        }
        pdc.set(PLAYER_PLACED_TAG, PersistentDataType.TAG_CONTAINER,playerPlacedPDC);
    }

    private static PersistentDataContainer getPlayerPlacedPDC(Chunk chunk) {
        PersistentDataContainer pdc = chunk.getPersistentDataContainer();
        return pdc.getOrDefault(PLAYER_PLACED_TAG, PersistentDataType.TAG_CONTAINER, pdc.getAdapterContext().newPersistentDataContainer());
    }

    private static NamespacedKey getKey(Block block) {
        int x = block.getX() & 0x000F;
        int y = block.getY() & 0x00FF;
        int z = block.getZ() & 0x000F;

        return new NamespacedKey(plugin, String.format("%d/%d/%d", x, y, z));
    }

}
