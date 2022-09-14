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

package com.jeff_media.jefflib.internal.nms.v1_19_1_R1;

import com.jeff_media.jefflib.internal.NMSReflUtils;
import com.jeff_media.jefflib.internal.nms.AbstractNMSMaterialHandler;
import net.minecraft.world.item.Item;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;

class MaterialHandler implements AbstractNMSMaterialHandler {

    private static final String ITEM_MAXSTACKSIZE_FIELD = "d";

    @Override
    public void setMaxStackSize(final Material material, final int maxStackSize) {
        NMSReflUtils.setField(Item.class, CraftMagicNumbers.getItem(material), ITEM_MAXSTACKSIZE_FIELD, maxStackSize);
    }
}
