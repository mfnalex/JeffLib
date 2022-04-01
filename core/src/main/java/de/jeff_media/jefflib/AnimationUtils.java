package de.jeff_media.jefflib;

import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Animation related methods. Currently only contains methods to play custom Totem animations
 */
@UtilityClass
public class AnimationUtils {

    /**
     * Plays the totem of undying animation to a given player. This is the same as <pre>playTotemAnimation(player, null)</pre>
     */
    public static void playTotemAnimation(@NotNull final Player player) {
        playTotemAnimation(player, null);
    }

    /**
     * Plays the totem of undying animation to a given player using the provided custom model data.
     * @param customModelData Custom model data to use, or null to not use any custom model data
     */
    public static void playTotemAnimation(@NotNull final Player player, @Nullable final Integer customModelData) {
        NMSNotSupportedException.check();
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
