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

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Inventory related methods
 */
@UtilityClass
public class InventoryUtils {

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

    /**
     * Counts how many times the given ItemStack is present in the given array
     */
    public static int countItemStacks(@NotNull final ItemStack[] array, @NotNull final ItemStack item) {
        return Arrays.stream(array).filter(Objects::nonNull).filter(itemStack -> itemStack.isSimilar(item)).mapToInt(ItemStack::getAmount).sum();
    }

    /**
     * Counts how many times the given Material is present in the given array
     */
    public static int countItemStacks(@NotNull final ItemStack[] array, @NotNull final Material material) {
        return Arrays.stream(array).filter(Objects::nonNull).filter(candidate -> candidate.getType() == material).mapToInt(ItemStack::getAmount).sum();
    }

    /**
     * Counts how many times the given ItemStack is present in the given inventory
     */
    public static int countItemStacks(@NotNull final Inventory inventory, @NotNull final ItemStack item) {
        return Arrays.stream(inventory.getContents()).filter(Objects::nonNull).filter(itemStack -> itemStack.isSimilar(item)).mapToInt(ItemStack::getAmount).sum();
    }

    /**
     * Counts how many times the given Material is present in the given inventory
     */
    public static int countMaterials(@NotNull final Inventory inventory, @NotNull final Material material) {
        return inventory.all(material).values().stream().filter(Objects::nonNull).mapToInt(ItemStack::getAmount).sum();
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the player's location
     *
     * @param player Player
     * @param items  ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(@NotNull final Player player, @NotNull final Iterable<ItemStack> items) {
        return addOrDrop(player, player.getLocation(), items);
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the given location
     *
     * @param player       Player
     * @param dropLocation Location where to drop leftover items
     * @param items        ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(@NotNull final Player player, @Nullable final Location dropLocation, @NotNull final Iterable<ItemStack> items) {
        boolean storedEverything = true;
        for (final ItemStack item : items) {
            for (final ItemStack leftover : player.getInventory().addItem(item).values()) {
                dropLocation.getWorld().dropItemNaturally(dropLocation, leftover);
                storedEverything = false;
            }
        }
        return storedEverything;
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the given location
     *
     * @param player       Player
     * @param dropLocation Location where to drop leftover items
     * @param items        ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(@NotNull final Player player, @Nullable final Location dropLocation, @NotNull final ItemStack... items) {
        return addOrDrop(player, dropLocation, Arrays.asList(items));
    }

    /**
     * Adds all the given ItemStacks to the player's inventory, or drops them at the player's location
     *
     * @param player Player
     * @param items  ItemStacks to add
     * @return true when all items could be stored, otherwise false
     */
    public static boolean addOrDrop(@NotNull final Player player, @NotNull final ItemStack... items) {
        return addOrDrop(player, player.getLocation(), Arrays.asList(items));
    }

    /**
     * Removes the given number of ItemStacks that match the given material from the inventory
     *
     * @return the number of ItemStacks that couldn't be removed (because the inventory didn't contain enough)
     */
    public static int removeX(final Inventory inventory, final Material toRemove, final int amountToRemove) {
        final HashMap<Integer, ? extends ItemStack> matchingStacks = inventory.all(toRemove);
        return removeItemStacks(amountToRemove, matchingStacks);
    }

    private static int removeItemStacks(int amountToRemove, final HashMap<Integer, ? extends ItemStack> matchingStacks) {
        for (final ItemStack item : matchingStacks.values()) {
            if (item.getAmount() <= amountToRemove) {
                amountToRemove -= item.getAmount();
                item.setAmount(0);
            } else {
                item.setAmount(item.getAmount() - amountToRemove);
            }
            if (amountToRemove == 0) return 0;
        }
        return amountToRemove;
    }

    /**
     * Removes the given number of ItemStacks that match the given item from the inventory
     *
     * @return the number of ItemStacks that couldn't be removed (because the inventory didn't contain enough)
     */
    public static int removeX(final Inventory inventory, final ItemStack toRemove, final int amountToRemove) {
        final HashMap<Integer, ? extends ItemStack> matchingStacks = getAll(inventory, toRemove);
        return removeItemStacks(amountToRemove, matchingStacks);
    }

    /**
     * Returns a map of all items from this inventory that match the given item. The key is the slot number where the stack was found.
     */
    public static HashMap<Integer, ? extends ItemStack> getAll(final Inventory inventory, final ItemStack item) {
        final HashMap<Integer, ItemStack> map = new HashMap<>();
        for (int i = 0; i < inventory.getSize(); i++) {
            final ItemStack tmp = inventory.getItem(i);
            if (tmp == null || tmp.getAmount() == 0) continue;
            if (tmp.isSimilar(item)) map.put(i, tmp);
        }
        return map;
    }

}
