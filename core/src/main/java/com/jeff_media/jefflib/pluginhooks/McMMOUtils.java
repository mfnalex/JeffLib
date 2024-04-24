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

package com.jeff_media.jefflib.pluginhooks;

import com.jeff_media.jefflib.EnchantmentUtils;
import com.jeff_media.jefflib.PDCUtils;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

/**
 * mcMMO related methods
 */
public final class McMMOUtils {

    private static final NamespacedKey SUPER_ABILITY_KEY = PDCUtils.getKeyFromString("mcmmo","super_ability_boosted");

    public static void removeSuperAbilityBoost(final ItemStack item) {
        if (item == null || !item.hasItemMeta()) return;
        if (!McMMOUtils.isSuperAbilityBoosted(item)) return;
        final int originalLevel = McMMOUtils.getSuperAbilityBoostedOriginalLevel(item);
        setEfficiency(item, originalLevel);
        PDCUtils.remove(item, SUPER_ABILITY_KEY);
    }

    public static boolean isSuperAbilityBoosted(final ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return false;
        return PDCUtils.has(itemStack, SUPER_ABILITY_KEY, PersistentDataType.INTEGER);
    }

    public static int getSuperAbilityBoostedOriginalLevel(final ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) return 0;
        return PDCUtils.getOrDefault(itemStack, SUPER_ABILITY_KEY, PersistentDataType.INTEGER, 0);
    }

    private static void setEfficiency(final ItemStack item, final int originalLevel) {
        final ItemMeta meta = item.getItemMeta();
        if (meta == null) return;
        meta.removeEnchant(EnchantmentUtils.DIG_SPEED_ENCHANTMENT);
        if (originalLevel != 0) {
            meta.addEnchant(EnchantmentUtils.DIG_SPEED_ENCHANTMENT, originalLevel, true);
        }
        item.setItemMeta(meta);
    }

}
