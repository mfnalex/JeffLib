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

    public ItemStack fromConfigurationSection(@NotNull final ConfigurationSection config) {
        return fromConfigurationSection(config, new HashMap<>());
    }

    private String replaceInString(String string, HashMap<String,String> placeholders) {
        for(Map.Entry<String, String> entry : placeholders.entrySet()) {
            if(entry.getKey()==null ||entry.getValue()==null) continue;
            string = string.replace(entry.getKey(), entry.getValue());
        }
        return string;
    }

    private List<String> replaceInString(List<String> strings, HashMap<String,String> placeholders) {
        for(int i = 0; i < strings.size(); i++) {
            strings.set(i, replaceInString(strings.get(i),placeholders));
        }
        return strings;
    }

    public static ItemStack fromConfigurationSection(@NotNull final ConfigurationSection config, HashMap<String,String> placeholders) {
        final String materialName = config.getString("material","BARRIER").toUpperCase(Locale.ROOT);

        int amount = 1;
        if(config.isSet("amount")) {
            if(config.isInt("amount")) {
                amount = config.getInt("amount");
            }
        }

        final ItemStack item = new ItemStack(Enums.getIfPresent(Material.class, materialName).or(Material.BARRIER), amount);

        List<String> lore = null;
        if(config.isSet("lore")) {
            if(config.isString("lore")) {
                lore.add(TextUtils.format(replaceInString(config.getString("lore"),placeholders)));
            } else {
                lore = TextUtils.format(replaceInString(config.getStringList("lore"),placeholders),null);
            }
        }

        String name = null;
        if(config.isSet("display-name")) {
            name = TextUtils.format(replaceInString(config.getString("display-name"),placeholders));
        }

        Integer modelData = null;
        if(config.isInt("custom-model-data")) {
            modelData = config.getInt("custom-model-data");
        }

        final int damage = config.getInt("damage",0);

        final ItemMeta meta = item.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);

        if(config.isConfigurationSection("enchantments")) {
            for(final String key : config.getConfigurationSection("enchantments").getKeys(false)) {
                final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                final int level = config.getConfigurationSection("enchantments").getInt(key);
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