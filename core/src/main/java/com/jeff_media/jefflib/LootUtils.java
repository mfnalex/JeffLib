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

import com.jeff_media.jefflib.internal.cherokee.Validate;
import java.util.Collection;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * {@link LootTable} related methods
 */
public final class LootUtils {


    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull LootTables lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull LootTables lootTable, @Nullable LootContext context) {
        return getRandomLoot(player, lootTable.getLootTable(), context);
    }

    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull LootTable lootTable, @Nullable LootContext context) {
        Validate.notNull(lootTable, "LootTable must not be null");
        Collection<ItemStack> result = lootTable.populateLoot(JeffLib.getRandom(), context != null ? context : getLootContext(player));
        if (result.isEmpty()) return null;
        return result.iterator().next();
    }

    public static LootContext getLootContext(@NotNull Player player) {
        return getLootContext(player, null, null, null);
    }

    public static LootContext getLootContext(@NotNull Player player, @Nullable Entity killed, @Nullable Float luck, @Nullable Integer lootModifier) {
        LootContext.Builder builder = new LootContext.Builder(player.getLocation());
        builder.killer(player);
        if (killed != null) {
            builder.lootedEntity(killed);
        }
        if (luck != null) {
            builder.luck(luck);
        }
        if (lootModifier != null) {
            builder.lootingModifier(lootModifier);
        }
        return builder.build();

    }

//    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull String lootTable) {
//        return getRandomLoot(player, lootTable, null);
//    }

//    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull String lootTable, @Nullable LootContext context) {
//        NamespacedKey key = NamespacedKey.fromString(lootTable);
//        Validate.notNull(key, "Not a valid NamespacedKey: " + lootTable);
//        return getRandomLoot(player, key, context);
//    }

    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull NamespacedKey lootTable, @Nullable LootContext context) {
        Validate.notNull(lootTable, "NamespacedKey must not be null");
        LootTable table = Bukkit.getLootTable(lootTable);
        Validate.notNull(table, "LootTable not found: " + lootTable);
        return getRandomLoot(player, table, context);
    }

    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull NamespacedKey lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

    public static @Nullable ItemStack getRandomLoot(@NotNull Player player, @NotNull LootTable lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

}
