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

package com.jeff_media.jefflib.internal.glowenchantment;

import com.jeff_media.jefflib.EnchantmentUtils;
import com.jeff_media.jefflib.ServerUtils;
import lombok.Getter;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;

import java.util.Objects;

public abstract class GlowEnchantmentFactory {

    @Getter private static final Enchantment instance;

    public static final NamespacedKey GLOW_ENCHANTMENT_KEY = Objects.requireNonNull(NamespacedKey.fromString("jefflib:glow"));

    static {
        Enchantment existing = Enchantment.getByKey(GLOW_ENCHANTMENT_KEY);
        if(existing != null) {
            instance = existing;
        } else {
            instance = ServerUtils.isRunningPaper() ? new PaperGlowEnchantment() : new SpigotGlowEnchantment();
        }
    }

    public static void register() {
//        Throwable throwable = null;
        try {
            EnchantmentUtils.registerEnchantment(instance);
        } catch (Throwable throwable1) {
//            throwable = throwable1;
        }

//        if(instance == null && throwable != null) {
//            throw new RuntimeException("Could not register nor get existing glow enchantment",throwable);
//        }
    }

}
