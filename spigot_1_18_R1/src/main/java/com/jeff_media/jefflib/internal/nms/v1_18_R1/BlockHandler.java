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

package com.jeff_media.jefflib.internal.nms.v1_18_R1;

import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import net.minecraft.world.level.block.ComposterBlock;

public class BlockHandler implements AbstractNMSBlockHandler {
    @Override
    public void playComposterParticlesAndSound(final org.bukkit.block.Block block, final boolean success) {
        ComposterBlock.handleFill(NMSInternals.getLevel(block.getWorld()), NMSInternals.getBlockPos(block), success);
    }
}
