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

import com.jeff_media.jefflib.ItemStackSerializer;
import com.jeff_media.jefflib.ItemStackUtils;
import com.jeff_media.jefflibtestplugin.NMSTest;
import com.jeff_media.jefflibtestplugin.TestException;
import com.jeff_media.jefflibtestplugin.TestRunner;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ItemStackToJson implements NMSTest {

    private final ItemStack item;

    {
        item = new ItemStack(Material.WOODEN_SWORD);
        item.setAmount(2);
        ItemStackUtils.setDisplayName(item, ChatColor.RED + "JeffLib NMSTest Item");
        ItemStackUtils.makeUnstackable(item);
    }

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        String json = ItemStackSerializer.toJson(item);
        runner.print(json);
        ItemStack cloned = ItemStackSerializer.fromJson(json);
        if (!item.equals(cloned) || !item.toString().equals(cloned.toString())) {
            throw new TestException("Itemstacks are not equal: " + item + " vs " + cloned);
        }
    }

}
