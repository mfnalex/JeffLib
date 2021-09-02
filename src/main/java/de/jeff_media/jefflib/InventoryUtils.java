package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Inventory related methods
 */
@UtilityClass
public final class InventoryUtils {

    /**
     * Checks whether the given InventoryDragEvent also affects the top Inventory.
     *
     * @param event InventoryDragEvent
     * @return true when the given InventoryDragEvent also affects the top inventory
     */
    public static boolean affectsTopInventory(final InventoryDragEvent event) {
        int minSlot = 999;
        for (final int i : event.getRawSlots()) {
            minSlot = Math.min(i, minSlot);
        }
        return minSlot < event.getView().getTopInventory().getSize();
    }

    public static int countItemStacks(@NotNull final Inventory inventory, @NotNull final ItemStack item) {
        final AtomicInteger amount = new AtomicInteger(0);
        Arrays.stream(inventory.getContents())
                .filter(itemStack -> itemStack.isSimilar(item))
                .forEach(itemStack -> amount.addAndGet(itemStack.getAmount()));
        return amount.get();
    }

    public static int countMaterials(@NotNull final Inventory inventory, @NotNull final Material material) {
        final AtomicInteger amount = new AtomicInteger(0);
        inventory.all(material).values().stream()
                .forEach(itemStack -> amount.addAndGet(itemStack.getAmount()));
        return amount.get();
    }
}
