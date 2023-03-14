package com.jeff_media.jefflib.internal;

import com.jeff_media.jefflib.EnchantmentUtils;
import com.jeff_media.jefflib.exceptions.ConflictingEnchantmentException;
import com.jeff_media.jefflib.internal.annotations.Paper;
import io.papermc.paper.enchantments.EnchantmentRarity;
import lombok.Getter;
import net.kyori.adventure.text.Component;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.entity.EntityCategory;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Set;

public class GlowEnchantment extends Enchantment {

    @Getter private static final Enchantment instance;
    private static final NamespacedKey GLOW_ENCHANTMENT_KEY = NamespacedKey.fromString("jefflib:glow");

    static {
        Enchantment existing = Enchantment.getByKey(GLOW_ENCHANTMENT_KEY);
        if(existing != null) {
            instance = existing;
        } else {
            instance = new GlowEnchantment();
            try {
                EnchantmentUtils.registerEnchantment(instance);
            } catch (ConflictingEnchantmentException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private GlowEnchantment() {
        super(GLOW_ENCHANTMENT_KEY);
    }

    @NotNull
    @Override
    public String getName() {
        return "Glow";
    }

    @Override
    public int getMaxLevel() {
        return 1;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @NotNull
    @Override
    @SuppressWarnings("deprecation")
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public boolean isCursed() {
        return false;
    }

    @Override
    public boolean conflictsWith(@NotNull Enchantment other) {
        return false;
    }

    @Override
    public boolean canEnchantItem(@NotNull ItemStack item) {
        return true;
    }

    @Override
    @Paper
    public @NotNull Component displayName(int i) {
        return Component.text(getName());
    }

    @Override
    public boolean isTradeable() {
        return false;
    }

    @Override
    public boolean isDiscoverable() {
        return false;
    }

    @Override
    public @NotNull EnchantmentRarity getRarity() {
        return EnchantmentRarity.COMMON;
    }

    @Override
    public float getDamageIncrease(int i, @NotNull EntityCategory entityCategory) {
        return 0;
    }

    @Override
    public @NotNull Set<EquipmentSlot> getActiveSlots() {
        return Collections.emptySet();
    }

    @Override
    public @NotNull String translationKey() {
        return "enchantment.jefflib.glow";
    }
}
