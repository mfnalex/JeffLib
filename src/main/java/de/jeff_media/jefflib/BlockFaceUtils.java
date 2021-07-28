package de.jeff_media.jefflib;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Ladder;

import static org.bukkit.block.BlockFace.*;

public class BlockFaceUtils {

    public static BlockFace getOpposite(BlockFace face) {
        switch (face) {
            case UP:
                return DOWN;
            case DOWN:
                return UP;
            case SOUTH:
                return NORTH;
            case NORTH:
                return SOUTH;
            case EAST:
                return WEST;
            case WEST:
                return EAST;
        }
        throw new IllegalArgumentException();
    }

    public static Block getSupportingBlock(Block directional) {
        if(directional.getBlockData() instanceof Directional) {
            return directional.getRelative(getOpposite(((Directional) directional.getBlockData()).getFacing()));
        }
        throw new IllegalArgumentException("Provided block is no instanceof Directional");
    }

    public static BlockFace getPlacedAgainstFace(Block existing, Block newBlock) {
        for(BlockFace blockFace : BlockFace.values()) {
            if(existing.getRelative(blockFace).equals(newBlock)) return blockFace;
        }
        throw new IllegalArgumentException("No BlockFace found");
    }
}
