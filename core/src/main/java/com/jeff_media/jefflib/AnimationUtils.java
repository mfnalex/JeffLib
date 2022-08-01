package com.jeff_media.jefflib;

import com.jeff_media.jefflib.internal.annotations.NMS;
import lombok.experimental.UtilityClass;
import org.bukkit.EntityEffect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Animation related methods. Currently only contains methods to play custom Totem animations
 */
@UtilityClass
public final class AnimationUtils {

    /**
     * Plays the totem of undying animation to a given player. This is the same as <pre>playTotemAnimation(player, null)</pre>
     * Unlike {@link Player#playEffect(EntityEffect)}, this will only be shown to the affected player.
     */
    @NMS
    public static void playTotemAnimation(@Nonnull final Player player) {
        playTotemAnimation(player, null);
    }

    /**
     * Plays the totem of undying animation to a given player using the provided custom model data.
     * Unlike {@link Player#playEffect(EntityEffect)}, this will only be shown to the affected player.
     *
     * @param customModelData Custom model data to use, or null to not use any custom model data
     */
    @NMS
    public static void playTotemAnimation(@Nonnull final Player player, @Nullable final Integer customModelData) {
        final ItemStack totem = new ItemStack(Material.TOTEM_OF_UNDYING);
        final ItemMeta meta = totem.getItemMeta();
        assert meta != null;
        meta.setCustomModelData(customModelData);
        totem.setItemMeta(meta);
        final ItemStack hand = player.getInventory().getItemInMainHand();
        player.getInventory().setItemInMainHand(totem);
        JeffLib.getNMSHandler().playTotemAnimation(player);
        player.getInventory().setItemInMainHand(hand);
    }
}
