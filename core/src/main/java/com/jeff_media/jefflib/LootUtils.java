package com.jeff_media.jefflib;

import com.jeff_media.jefflib.internal.cherokee.Validate;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.loot.LootContext;
import org.bukkit.loot.LootTable;
import org.bukkit.loot.LootTables;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

/**
 * {@link LootTable} related methods
 */
public final class LootUtils {


    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull LootTables lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull LootTables lootTable, @Nullable LootContext context) {
        return getRandomLoot(player, lootTable.getLootTable(), context);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull LootTable lootTable, @Nullable LootContext context) {
        Validate.notNull(lootTable, "LootTable must not be null");
        Collection<ItemStack> result = lootTable.populateLoot(JeffLib.getRandom(), context != null ? context : getLootContext(player));
        if (result.isEmpty()) return null;
        return result.iterator().next();
    }

    public static LootContext getLootContext(@Nonnull Player player) {
        return getLootContext(player, null, null, null);
    }

    public static LootContext getLootContext(@Nonnull Player player, @Nullable Entity killed, @Nullable Float luck, @Nullable Integer lootModifier) {
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

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull String lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull String lootTable, @Nullable LootContext context) {
        NamespacedKey key = NamespacedKey.fromString(lootTable);
        Validate.notNull(key, "Not a valid NamespacedKey: " + lootTable);
        return getRandomLoot(player, key, context);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull NamespacedKey lootTable, @Nullable LootContext context) {
        Validate.notNull(lootTable, "NamespacedKey must not be null");
        LootTable table = Bukkit.getLootTable(lootTable);
        Validate.notNull(table, "LootTable not found: " + lootTable);
        return getRandomLoot(player, table, context);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull NamespacedKey lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

    public static @Nullable ItemStack getRandomLoot(@Nonnull Player player, @Nonnull LootTable lootTable) {
        return getRandomLoot(player, lootTable, null);
    }

}
