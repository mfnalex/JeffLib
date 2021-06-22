package de.jeff_media.jefflib;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentUtils {

    /**
     * Returns the level of an enchantment on the given item, or 0 if it's not enchanted with this enchantment
     * @param item Item to check
     * @param enchantment Enchantment to check
     * @return Level of the enchantmant, or 0 if not present
     */
    public static int getLevel(ItemStack item, Enchantment enchantment) {
        ItemMeta meta = item.getItemMeta();
        if (meta.hasEnchant(enchantment)) {
            return meta.getEnchantLevel(enchantment);
        }
        return 0;
    }

}
