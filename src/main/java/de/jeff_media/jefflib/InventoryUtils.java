package de.jeff_media.jefflib;

import org.bukkit.event.inventory.InventoryDragEvent;

/**
 * Inventory related methods
 */
public class InventoryUtils {

    /**
     * Checks whether the given InventoryDragEvent also affects the top Inventory.
     * @param event InventoryDragEvent
     * @return true when the given InventoryDragEvent also affects the top inventory
     */
    public static boolean affectsTopInventory(InventoryDragEvent event) {
        int minSlot = 999;
        for (final int i : event.getRawSlots()) {
            minSlot = Math.min(i, minSlot);
        }
        if (minSlot < event.getView().getTopInventory().getSize()) {
            return true;
        }
        return false;
    }
}
