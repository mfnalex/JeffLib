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

import com.jeff_media.jefflib.exceptions.ConflictingEnchantmentException;
import com.jeff_media.jefflib.internal.glowenchantment.GlowEnchantmentFactory;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Enchantment related methods
 */
@UtilityClass
public class EnchantmentUtils {

    /**
     * Registers a custom enchantment. You should not do that, but instead rely on PDC tags to identify your custom enchantments.
     * The Enchantment class wasn't meant to be expanded by plugin enchantments, and your custom enchantment will also be lost
     * when playes use an enchantment table on the item.
     *
     * @param enchantment Enchantment to register
     */
    public void registerEnchantment(final Enchantment enchantment) throws ConflictingEnchantmentException {
        try {
            final Field fieldAcceptingNew = Enchantment.class.getDeclaredField("acceptingNew");
            fieldAcceptingNew.setAccessible(true);
            fieldAcceptingNew.set(null, true);
            fieldAcceptingNew.setAccessible(false);

            Enchantment.registerEnchantment(enchantment);
        } catch (final Throwable exception) {
            throw new ConflictingEnchantmentException(exception.getMessage());
        }
    }

    /**
     * Applies an enchanted book to an item. This will not apply incompatible enchantments (either because the target item type doesn't support it, or because it conflicts with an existing enchantment), unless {@param ignoreRestrictions} is set to true.
     * If the target item already has an enchantment of the same type, the higher level will be applied. If both levels are the same, the level will be increased by 1.
     *
     * @param book               Book to apply
     * @param target             Target item
     * @param ignoreRestrictions Whether to ignore restrictions
     */
    public static void applyBook(final EnchantmentStorageMeta book, final ItemStack target, final boolean ignoreRestrictions) {
        final Map<Enchantment, Integer> existingEnchantments = target.getEnchantments();
        final ItemMeta meta = Objects.requireNonNull(target.getItemMeta());

        newEnchantments:
        for (final Map.Entry<Enchantment, Integer> entry : book.getStoredEnchants().entrySet()) {
            Enchantment enchant = entry.getKey();
            int level = entry.getValue();
            if (!ignoreRestrictions) {
                if (!enchant.canEnchantItem(target)) {
                    continue;
                }

                existingEnchantments:
                for (Enchantment existingEnchant : existingEnchantments.keySet()) {
                    if (!enchant.conflictsWith(existingEnchant)) {
                        continue newEnchantments;
                    }
                }
            }
            final int existingLevel = existingEnchantments.getOrDefault(enchant, 0);
            if (existingLevel > level) {
                continue;
            }
            if (existingLevel == level) {
                level++;
            }
            meta.addEnchant(enchant, level, true);
        }
        target.setItemMeta(meta);
    }

    /**
     * Returns the level of an enchantment on the given item, or 0 if it's not enchanted with this enchantment
     *
     * @param item        Item to check
     * @param enchantment Enchantment to check
     * @return Level of the enchantmant, or 0 if not present
     */
    public static int getLevel(final ItemStack item, final Enchantment enchantment) {
        if (!item.hasItemMeta()) {
            return 0;
        }
        final ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if (meta.hasEnchant(enchantment)) {
            return meta.getEnchantLevel(enchantment);
        }
        return 0;
    }

    /**
     * Parses Enchantments including levels from a {@link ConfigurationSection}. The given ConfigurationSection should look like this:
     * <pre>
     * efficiency: 2 # Efficiency Level 2
     * fortune: 1 # Fortune Level 1
     * </pre>
     *
     * @param section ConfigurationSection to parse
     * @return A Map&lt;Enchantment,Integer> containing the given enchantments mapped to their given levels
     */
    @NotNull
    public static Map<Enchantment, Integer> fromConfigurationSection(final ConfigurationSection section) {
        final Map<Enchantment, Integer> map = new HashMap<>();
        for (final String enchantName : section.getKeys(false)) {
            final Enchantment enchant = getByName(enchantName);
            final int level = section.getInt(enchantName);
            if (enchant != null) {
                map.put(enchant, level);
            }
        }
        return map;
    }

    /**
     * Gets an enchant by name (case-insensitive), e.g. "unbreaking" or "fortune".
     *
     * @param name Name of the enchantment (case-insensitive)
     * @return The enchantment of the specified name
     */
    @Nullable
    public static Enchantment getByName(@NotNull final String name) {
        for (final Enchantment enchant : Enchantment.values()) {
            if (enchant.getKey().getKey().equalsIgnoreCase(name)) {
                return enchant;
            }
        }
        return null;
    }

    /**
     * Adds a glowing effect to the given item (doesn't work on all items)
     *
     * @param item ItemStack to add the effect to
     * @return true if the effect was added, false if it was already present or if the item has no ItemMeta
     */
    public static boolean addGlowEffect(@NotNull ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        boolean result = addGlowEffect(meta);
        if (result) {
            item.setItemMeta(meta);
        }
        return result;
    }

    /**
     * Adds a glowing effect to the given item (doesn't work on all items)
     *
     * @param meta ItemMeta to add the effect to
     * @return true if the effect was added, false if it was already present
     */
    public static boolean addGlowEffect(@NotNull ItemMeta meta) {
        if (meta.hasEnchant(GlowEnchantmentFactory.getInstance())) {
            return false;
        }
        meta.addEnchant(GlowEnchantmentFactory.getInstance(), 1, true);
        return true;
    }

}
