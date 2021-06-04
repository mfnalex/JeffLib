package de.jeff_media.jefflib;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

public class BlockTracker {

    private static final Plugin plugin = JeffLib.getPlugin();
    private static final NamespacedKey PLAYER_PLACED_TAG = new NamespacedKey(plugin, "playerplaced");
    private static final Collection<Material> TRACKED_TYPES = new HashSet<>();

    public static void addTrackedBlockTypes(Collection<Material> types) {
        TRACKED_TYPES.addAll(types);
    }

    public static Collection<Material> getTrackedBlockTypes() {
        return TRACKED_TYPES;
    }

    public static void trackAllBlockTypes() {
        addTrackedBlockTypes(Arrays.asList(Material.values()));
    }

    public static void clearTrackedBlockTypes() {
        TRACKED_TYPES.clear();
    }

    public static boolean isTrackedBlockType(Material type) {
        return TRACKED_TYPES.contains(type);
    }

    public static void removeTrackedBlockTypes(Collection<Material> types) {
        TRACKED_TYPES.removeAll(types);
    }

    public static boolean isPlayerPlacedBlock(Block block) {
        PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        return playerPlacedPDC.has(getKey(block), PersistentDataType.BYTE);
    }

    public static void setPlayerPlacedBlock(Block block, boolean playerPlaced) {
        PersistentDataContainer pdc = block.getChunk().getPersistentDataContainer();
        PersistentDataContainer playerPlacedPDC = getPlayerPlacedPDC(block.getChunk());
        NamespacedKey key = getKey(block);
        if (playerPlaced) {
            playerPlacedPDC.set(key, PersistentDataType.BYTE, (byte) 1);
            System.out.println("Registering player placed block");
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
