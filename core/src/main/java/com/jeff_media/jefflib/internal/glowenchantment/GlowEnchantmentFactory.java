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
