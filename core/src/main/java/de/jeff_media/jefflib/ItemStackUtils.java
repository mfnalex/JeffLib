package de.jeff_media.jefflib;

import com.google.common.base.Enums;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * ItemStack related methods
 */
@UtilityClass
public final class ItemStackUtils {

    public static boolean isNullOrEmpty(final ItemStack itemStack) {
        return itemStack == null || itemStack.getType() == Material.AIR || itemStack.getAmount() == 1;
    }

    public static ItemStack fromConfigurationSection(@NotNull final ConfigurationSection config) {
        String materialName = config.getString("material","BARRIER").toUpperCase(Locale.ROOT);

        int amount = 1;
        if(config.isSet("amount")) {
            if(config.isInt("amount")) {
                amount = config.getInt("amount");
            }
        }

        ItemStack item = new ItemStack(Enums.getIfPresent(Material.class, materialName).or(Material.BARRIER), amount);

        List<String> lore = null;
        if(config.isSet("lore")) {
            if(config.isString("lore")) {
                lore.add(TextUtils.format(config.getString("lore")));
            } else {
                lore = TextUtils.format(config.getStringList("lore"),null);
            }
        }

        String name = null;
        if(config.isSet("display-name")) {
            name = TextUtils.format(config.getString("display-name"));
        }

        Integer modelData = null;
        if(config.isInt("custom-model-data")) {
            modelData = config.getInt("custom-model-data");
        }

        int damage = config.getInt("damage",0);

        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);

        if(config.isConfigurationSection("enchantments")) {
            for(String key : config.getConfigurationSection("enchantments").getKeys(false)) {
                Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                int level = config.getConfigurationSection("enchantments").getInt(key);
                meta.addEnchant(enchantment, level,true);
            }
        }

        if(meta instanceof Damageable) {
            ((Damageable)meta).setDamage(damage);
        }

        item.setItemMeta(meta);
        return item;
    }

    /**
     * Merges ItemStacks when possible
     *
     * @param items ItemStacks to merge
     * @return merged ItemStacks
     */
    public static List<ItemStack> mergeItemStacks(ItemStack... items) {
        HashMap<Integer, ItemStack> overflow = null;
        items = getNonNullItems(items);
        final List<ItemStack> mergedItems = new ArrayList<>();
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
     *
     * @param items ItemStacks to check
     * @return All given ItemStacks that are neither null nor air
     */
    public static ItemStack[] getNonNullItems(final ItemStack... items) {
        final ArrayList<ItemStack> nonNullItems = new ArrayList<>();
        for (final ItemStack item : items) {
            if (!isNullOrEmpty(item)) nonNullItems.add(item);
        }
        return nonNullItems.toArray(new ItemStack[0]);
    }

    public static void setDisplayName(@NotNull final ItemStack item, @NotNull final String name) {
        final ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setItemMeta(meta);
    }

}