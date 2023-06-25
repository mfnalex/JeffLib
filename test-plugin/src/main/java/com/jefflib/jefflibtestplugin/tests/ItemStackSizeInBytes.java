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

package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.ItemStackUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import java.util.Objects;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

public class ItemStackSizeInBytes implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        ItemStack item = new ItemStack(Material.WRITTEN_BOOK);
        BookMeta meta = Objects.requireNonNull((BookMeta) item.getItemMeta());
        meta.addPage("Hello world");
        meta.addPage("Second page");
        item.setItemMeta(meta);
        runner.print("ItemStack size: " + ItemStackUtils.getSizeInBytes(item) + " bytes");
    }
}
