package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;

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

    public static int countItemStacks(@NotNull final ItemStack[] inventory, @NotNull final ItemStack item) {
        final AtomicInteger amount = new AtomicInteger(0);
        Arrays.stream(inventory)
                .filter(itemStack -> itemStack.isSimilar(item))
                .forEach(itemStack -> amount.addAndGet(itemStack.getAmount()));
        return amount.get();
    }

    public static int countItemStacks(@NotNull final ItemStack[] inventory, @NotNull final Material material) {
        final AtomicInteger amount = new AtomicInteger(0);
        Arrays.stream(inventory)
                .filter(candidate -> candidate.getType() == material)
                .forEach(itemStack -> amount.addAndGet(itemStack.getAmount()));
        return amount.get();
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
        inventory.all(material).values()
                .forEach(itemStack -> amount.addAndGet(itemStack.getAmount()));
        return amount.get();
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the given location
     * @param player Player
     * @param dropLocation Location where to drop leftover items
     * @param items ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(final @NotNull Player player, final @Nullable Location dropLocation, final @NotNull Iterable<ItemStack> items) {
        boolean storedEverything = true;
        for(final ItemStack item : items) {
            player.getInventory().addItem(item).values().forEach((leftover) -> dropLocation.getWorld().dropItemNaturally(dropLocation, leftover));
            storedEverything = false;
        }
        return storedEverything;
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the player's location
     * @param player Player
     * @param items ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(final @NotNull Player player, final @NotNull Iterable<ItemStack> items) {
        return addOrDrop(player, player.getLocation(), items);
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the given location
     * @param player Player
     * @param dropLocation Location where to drop leftover items
     * @param items ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(final @NotNull Player player, final @Nullable Location dropLocation, final @NotNull ItemStack... items) {
        return addOrDrop(player, dropLocation, Arrays.asList(items));
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the player's location
     * @param player Player
     * @param items ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(final @NotNull Player player, final @NotNull ItemStack... items) {
        return addOrDrop(player, player.getLocation(), Arrays.asList(items));
    }

}
