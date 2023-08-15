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

package com.jeff_media.jefflibtestplugin.tests;

import com.jeff_media.jefflib.EnchantmentUtils;
import com.jeff_media.jefflibtestplugin.NMSTest;
import com.jeff_media.jefflibtestplugin.TestRunner;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

public class GlowEnchantment implements NMSTest {

    private Player player;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        ItemStack item = new ItemStack(Material.DIAMOND);
        EnchantmentUtils.addGlowEffect(item);
        if (player != null) {
            this.player = player;
            player.getInventory().setItemInMainHand(item);
        }
    }

    @Override
    public boolean isRunnableFromConsole() {
        return true;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Does the diamond glow?";
    }

    @Override
    public void cleanup() {
        if (player != null) {
            player.getInventory().setItemInMainHand(null);
        }
    }
}
