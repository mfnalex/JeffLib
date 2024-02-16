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


import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.craftbukkit.v1_20_R3.enchantments.CraftEnchantment;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class NMS extends JavaPlugin implements Listener {

    {
        // Create Item
        ItemStack item = new ItemStack(Material.POTION);

        // Get meta
        PotionMeta meta = (PotionMeta) item.getItemMeta();

        // Create custom effect
        PotionEffect effect = new PotionEffect(PotionEffectType.INVISIBILITY, /* Duration */30 * 20, /*Amplifier/Level*/1);

        // Add effect to meta
        meta.addCustomEffect(effect, false);

        // Set meta to item
        item.setItemMeta(meta);
    }

    static class NmsGlowEnchantment extends Enchantment {

        static NmsGlowEnchantment INSTANCE = new NmsGlowEnchantment();

        protected NmsGlowEnchantment() {
            super(Rarity.COMMON, EnchantmentCategory.BREAKABLE, EquipmentSlot.values());
        }
    }

    static class CraftGlowEnchantment extends CraftEnchantment {

        private static final NamespacedKey KEY = NamespacedKey.fromString("whatever:glow");

        public CraftGlowEnchantment() {
            super(KEY, NmsGlowEnchantment.INSTANCE);
        }

        @Override
        public boolean isCursed() {
            return true; // Custom enchantments *are* cursed in 1.20.3+
        }
    }


}


