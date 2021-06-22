package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ItemStackUtils {

    public static boolean isNullOrEmpty(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 1;
    }

    /**
     * Merges ItemStacks
     * @param items ItemStacks to merge
     * @return merged ItemStacks
     */
    public static List<ItemStack> mergeItemStacks(ItemStack... items) {
        HashMap<Integer, ItemStack> overflow = null;
        items = getNonNullItems(items);
        List<ItemStack> mergedItems = new ArrayList<>();
        Inventory inventory;

        do {
            inventory = Bukkit.createInventory(null, 54);
            overflow = inventory.addItem(overflow == null ? items : overflow.values().toArray(new ItemStack[0]));
            mergedItems.addAll(Arrays.asList(inventory.getContents()));
        } while (!overflow.isEmpty());

        return mergedItems;
    }

    /**
     * Returns an array of all given ItemStacks that are neither null nor AIR
     * @param items ItemStacks to check
     * @return All given ItemStacks that are neither null nor air
     */
    public static ItemStack[] getNonNullItems(ItemStack... items) {
        ArrayList<ItemStack> nonNullItems = new ArrayList<>();
        for (ItemStack item : items) {
            if (!isNullOrEmpty(item)) nonNullItems.add(item);
        }
        return nonNullItems.toArray(new ItemStack[0]);
    }
}