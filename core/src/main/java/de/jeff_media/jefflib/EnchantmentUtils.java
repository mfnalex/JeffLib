package de.jeff_media.jefflib;

import de.jeff_media.jefflib.data.tuples.Pair;
import lombok.experimental.UtilityClass;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Enchantment related methods
 */
@UtilityClass
public final class EnchantmentUtils {

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

    @Nullable
    public static Enchantment getByName(@NotNull final String name) {
        for(Enchantment enchant : Enchantment.values()) {
            if(enchant.getKey().getKey().equalsIgnoreCase(name)) {
                return enchant;
            }
        }
        return null;
    }

    public static @NotNull List<Pair<Enchantment,Integer>> fromConfigurationSection(final ConfigurationSection section) {
        final List<Pair<Enchantment,Integer>> list = new ArrayList<>();
        for(final String enchantName : section.getKeys(false)) {
            final Enchantment enchant = getByName(enchantName);
            final int level = section.getInt(enchantName);
            if(enchant != null) {
                list.add(new Pair<>(enchant, level));
            }
        }
        return list;
    }

}
