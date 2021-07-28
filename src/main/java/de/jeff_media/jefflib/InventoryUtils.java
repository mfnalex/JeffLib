package de.jeff_media.jefflib;

import org.bukkit.event.inventory.InventoryDragEvent;

public class InventoryUtils {

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
