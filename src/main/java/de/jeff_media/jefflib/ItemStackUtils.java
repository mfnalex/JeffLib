package de.jeff_media.jefflib;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.stream.Collectors;

public class ItemStackUtils {

    public static boolean isNullOrEmpty(ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 1;
    }

    public static List<ItemStack> mergeItemStacks(ItemStack ... items) {
        HashMap<Integer,ItemStack> overflow = null;
        items = getNonNullItems(items);
        List<ItemStack> mergedItems = new ArrayList<>();
        Inventory inventory;

        do {
            inventory = Bukkit.createInventory(null, 54);
            overflow = inventory.addItem(overflow == null ? items : overflow.values().toArray(new ItemStack[overflow.size()]));
            mergedItems.addAll(Arrays.asList(inventory.getContents()));
        } while (!overflow.isEmpty());

        return mergedItems;
    }

    public static ItemStack[] getNonNullItems(ItemStack... items) {
        ArrayList<ItemStack> nonNullItems = new ArrayList<>();
        for(ItemStack item : items) {
            if(!isNullOrEmpty(item)) nonNullItems.add(item);
        }
        return nonNullItems.toArray(new ItemStack[nonNullItems.size()]);
    }
}