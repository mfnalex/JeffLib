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

package com.jeff_media.jefflib;

import static org.bukkit.block.BlockFace.DOWN;
import static org.bukkit.block.BlockFace.UP;
import com.jeff_media.jefflib.exceptions.UtilityClassInstantiationException;
import lombok.experimental.UtilityClass;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;

/**
 * BlockFace related methods
 */
public final class BlockFaceUtils {

    private BlockFaceUtils() {
        throw new UtilityClassInstantiationException();
    }

    /**
     * Gets the block another block (e.g. a ladder) is attached to
     *
     * @param directional Block to check
     * @return Block that supports the block to check
     */
    public static Block getSupportingBlock(final Block directional) {
        if (directional.getBlockData() instanceof Directional) {
            return directional.getRelative(((Directional) directional.getBlockData()).getFacing().getOppositeFace());
        }
        throw new IllegalArgumentException("Provided Block's BlockData is no instanceof Directional");
    }

    /**
     * Gets the BlockFace of the existing block that must have been right-clicked to place the new Block
     *
     * @param existing Existing block
     * @param newBlock New block
     * @return Existing block's BlockFace that must have been right-clicked to place the new block
     */
    public static BlockFace getPlacedAgainstFace(final Block existing, final Block newBlock) {
        for (final BlockFace blockFace : BlockFace.values()) {
            if (existing.getRelative(blockFace).equals(newBlock)) return blockFace;
        }
        throw new IllegalArgumentException("No BlockFace found");
    }

    /**
     * Gets the "other part" of this bisected block. E.g. if the block is a top door part, this will return the bottom door part.
     *
     * @param block Bisected block to check
     * @return The "other part" of the bisected block
     * @throws IllegalArgumentException if the block is not a bisected block
     */
    public static Block getOppositeOfBisected(final Block block) {
        BlockData data = block.getBlockData();
        if (!(data instanceof Bisected)) {
            throw new IllegalArgumentException("Given block's data must be instanceof Bisected");
        }
        Bisected bisected = (Bisected) block.getBlockData();
        return bisected.getHalf() == Bisected.Half.TOP ? block.getRelative(DOWN) : block.getRelative(UP);
    }
}
