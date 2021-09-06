package de.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

}
