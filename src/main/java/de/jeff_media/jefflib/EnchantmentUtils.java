package de.jeff_media.jefflib;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class EnchantmentUtils {

    public static int getLevel(ItemStack item, Enchantment enchantment) {
        ItemMeta meta = item.getItemMeta();
        if(meta.hasEnchant(enchantment)) {
            return meta.getEnchantLevel(enchantment);
        }
        return 0;
    }

}
