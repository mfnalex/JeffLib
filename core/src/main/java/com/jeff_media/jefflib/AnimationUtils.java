/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

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
public class AnimationUtils {

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
