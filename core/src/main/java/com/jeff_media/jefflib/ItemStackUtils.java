/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib;

import com.google.common.base.Enums;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.util.*;

/**
 * ItemStack related methods
 */
@UtilityClass
public class ItemStackUtils {

    /**
     * Magic value to identify "no NBT data"
     *
     * @see #getSizeInBytes(ItemStack)
     */
    public static final int NO_DATA = -1;
    /**
     * Magic value to identify "could not parse NBT data"
     *
     * @see #getSizeInBytes(ItemStack)
     */
    public static final int ERROR_READING_DATA = -2;

    /**
     * Gets the size of an ItemStack's NBT data, or {@link #NO_DATA} if it doesn't have any, or {@link #ERROR_READING_DATA} if the data couldn't be parsed
     *
     * @return NBT data size in byteds, or {@link #NO_DATA} if it doesn't have any, or {@link #ERROR_READING_DATA} if the data couldn't be parsed
     */
    public static int getSizeInBytes(@NonNull final ItemStack itemStack) {
        try {
            return JeffLib.getNMSHandler().getItemStackSizeInBytes(itemStack);
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR_READING_DATA;
        }
    }

    /**
     * Parses an ItemStack from a ConfigurationSection without applying any placeholder values.
     *
     * @see #fromConfigurationSection(ConfigurationSection, HashMap)
     */
    public ItemStack fromConfigurationSection(@Nonnull final ConfigurationSection config) {
        return fromConfigurationSection(config, new HashMap<>());
    }

    /**
     * Parses an ItemStack from a ConfigurationSection and applies placeholders. Example ConfigurationSection:
     * <pre>
     * material: PLAYER_HEAD
     * base64: "someBase64String"
     * amount: 1
     * lore:
     *   - "first line"
     * display-name: "name"
     * custom-model-data: 2
     * damage: 100
     * prevent-stacking: false
     * enchantments:
     *   unbreaking: 1
     *   efficiency: 5
     * </pre>
     *
     * @see TextUtils#replaceInString(String, Map)
     */
    public static ItemStack fromConfigurationSection(@Nonnull final ConfigurationSection config, final HashMap<String, String> placeholders) {
        final String materialName = config.getString("material", "BARRIER").toUpperCase(Locale.ROOT);

        int amount = 1;
        if (config.isSet("amount")) {
            if (config.isInt("amount")) {
                amount = config.getInt("amount");
            }
        }

        final ItemStack item;

        if (materialName.equalsIgnoreCase("PLAYER_HEAD") && config.isSet("base64") && config.isString("base64")) {
            item = SkullUtils.getHead(Objects.requireNonNull(config.getString("base64")));
            //System.out.println("Creating custom head with base64" + config.getString("base64"));
        } else {
            item = new ItemStack(Enums.getIfPresent(Material.class, materialName).or(Material.BARRIER), amount);
        }

        List<String> lore = new ArrayList<>();
        if (config.isSet("lore")) {
            if (config.isString("lore")) {
                lore.add(TextUtils.format(TextUtils.replaceInString(config.getString("lore"), placeholders)));
            } else {
                lore = TextUtils.format(TextUtils.replaceInString(config.getStringList("lore"), placeholders), null);
            }
        }

        String name = null;
        if (config.isSet("display-name")) {
            name = TextUtils.format(TextUtils.replaceInString(config.getString("display-name"), placeholders));
        }

        Integer modelData = null;
        if (config.isInt("custom-model-data")) {
            modelData = config.getInt("custom-model-data");
        }

        final int damage = config.getInt("damage", 0);

        final ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setLore(lore);
        meta.setDisplayName(name);
        meta.setCustomModelData(modelData);

        if (config.isConfigurationSection("enchantments")) {
            for (final String key : Objects.requireNonNull(config.getConfigurationSection("enchantments")).getKeys(false)) {
                final Enchantment enchantment = Enchantment.getByKey(NamespacedKey.minecraft(key));
                if (enchantment == null) {
                    throw new IllegalArgumentException("Unknown enchantment: " + key);
                }
                final int level = Objects.requireNonNull(config.getConfigurationSection("enchantments")).getInt(key, 1);
                meta.addEnchant(enchantment, level, true);
            }
        }

        if (meta instanceof Damageable) {
            ((Damageable) meta).setDamage(damage);
        }

        if(config.getBoolean("prevent-stacking", false)) {
            meta.getPersistentDataContainer().set(PDCUtils.getKey("prevent-stacking"), PersistentDataType.STRING, UUID.randomUUID().toString());
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

    /**
     * Checks if an ItemStack is null or empty (empty = amount of 0 or if the type is air)
     */
    public static boolean isNullOrEmpty(@Nullable final ItemStack itemStack) {
        return itemStack == null || itemStack.getType().isAir() || itemStack.getAmount() == 0;
    }

    /**
     * Applies a display name to the given item
     */
    public static void setDisplayName(@Nonnull final ItemStack item, @Nonnull final String name) {
        final ItemMeta meta = item.getItemMeta();
        Objects.requireNonNull(meta).setDisplayName(name);
        item.setItemMeta(meta);
    }

    /**
     * Damages given ItemStack by specified amount
     *
     * @param amount damage amount to be applied
     * @param item   ItemStack to be damaged
     * @param player Player who damaged the item
     */
    public static void damageItem(int amount, final @Nonnull ItemStack item, @Nullable final Player player) {
        final ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable) || amount < 0) return;
        final int durability = item.getEnchantmentLevel(Enchantment.DURABILITY);
        int k = 0;
        for (int l = 0; durability > 0 && l < amount; l++) {
            if (JeffLib.getRandom().nextInt(durability + 1) > 0) {
                k++;
            }
        }
        amount -= k;
        if (player != null) {
            final PlayerItemDamageEvent damageEvent = new PlayerItemDamageEvent(player, item, amount);
            Bukkit.getServer().getPluginManager().callEvent(damageEvent);
            if (amount != damageEvent.getDamage() || damageEvent.isCancelled()) {
                damageEvent.getPlayer().updateInventory();
            } else if (damageEvent.isCancelled()) {
                return;
            }
            amount = damageEvent.getDamage();

        }
        if (amount <= 0) return;

        final Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage() + amount);
        item.setItemMeta(meta);
    }

    /**
     * Makes an item unstackable by adding a random UUID to the item's "prevent-stacking" PDC tag
     */
    public static void makeUnstackable(ItemStack item) {
        PDCUtils.set(item, "prevent-stacking", PersistentDataType.STRING, UUID.randomUUID().toString());
    }

}