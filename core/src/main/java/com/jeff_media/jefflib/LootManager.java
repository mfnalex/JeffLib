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

import com.google.common.base.Enums;
import com.jeff_media.jefflib.data.VariableItemStack;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Deprecated
public class LootManager {

    final Map<Enum<?>, List<VariableItemStack>> map = new HashMap<>();

    public static LootManager fromConfigurationSection(final ConfigurationSection config) {
        final LootManager lootManager = new LootManager();
        for (final String key : config.getKeys(false)) {
            final ConfigurationSection section = config.getConfigurationSection(key);
            final VariableItemStack itemStack = VariableItemStack.fromConfigurationSection(section);

            final Material matKey = Enums.getIfPresent(Material.class, key.toUpperCase(Locale.ROOT)).orNull();
            final EntityType entityKey = Enums.getIfPresent(EntityType.class, key.toUpperCase(Locale.ROOT)).orNull();

            if (matKey != null) {
                lootManager.add(matKey, itemStack);
            } else if (entityKey != null) {
                lootManager.add(entityKey, itemStack);
            }
        }
        return lootManager;
    }

    private void add(final Enum<?> key, final VariableItemStack item) {
        if (map.containsKey(key)) {
            map.get(key).add(item);
        } else {
            final List<VariableItemStack> list = new ArrayList<>();
            list.add(item);
            map.put(key, list);
        }
    }

    public List<ItemStack> getDrops(final Entity entity) {
        return getDrops(entity.getType());
    }

    public List<ItemStack> getDrops(final Enum<?> key) {
        final List<ItemStack> drops = new ArrayList<>();
        if (map.containsKey(key)) {
            for (final VariableItemStack drop : map.get(key)) {
                if (drop.getChance() >= 100 || RandomUtils.getDouble(0, 100) <= drop.getChance()) {
                    drops.add(drop.getItemStack());
                }
            }
        }
        return drops;
    }

    public List<ItemStack> getDrops(final Material mat) {
        return getDrops((Enum<?>) mat); // Casting is needed to avoid calling this function
    }

}
