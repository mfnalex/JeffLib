package de.jeff_media.jefflib;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Attachable;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.type.Ladder;

import static org.bukkit.block.BlockFace.*;

/**
 * Methods related to BlocKFaces
 */
public class BlockFaceUtils {

    /**
     * Returns the opposite BlockFace for a given BlockFace. E.g. EAST_NORTH_EAST will return WEST_SOUTH_WEST. SELF will return SELF.
     * @param face Original BlockFace
     * @return Opposite BlockFace
     */
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

            case SOUTH_EAST:
                return NORTH_WEST;
            case SOUTH_WEST:
                return NORTH_EAST;

            case SOUTH_SOUTH_EAST:
                return NORTH_NORTH_WEST;
            case SOUTH_SOUTH_WEST:
                return NORTH_NORTH_EAST;

            case NORTH_EAST:
                return SOUTH_WEST;
            case NORTH_WEST:
                return SOUTH_EAST;

            case NORTH_NORTH_EAST:
                return SOUTH_SOUTH_WEST;
            case NORTH_NORTH_WEST:
                return SOUTH_SOUTH_EAST;

            case EAST_NORTH_EAST:
                return WEST_SOUTH_WEST;
            case EAST_SOUTH_EAST:
                return WEST_NORTH_WEST;

            case WEST_NORTH_WEST:
                return EAST_SOUTH_EAST;
            case WEST_SOUTH_WEST:
                return EAST_NORTH_EAST;

            case SELF:
                return SELF;

        }
        throw new IllegalArgumentException();
    }

    /**
     * Gets the block another block (e.g. a ladder) is attached to
     * @param directional Block to check
     * @return Block that supports the block to check
     */
    public static Block getSupportingBlock(Block directional) {
        if(directional.getBlockData() instanceof Directional) {
            return directional.getRelative(getOpposite(((Directional) directional.getBlockData()).getFacing()));
        }
        throw new IllegalArgumentException("Provided Block's BlockData is no instanceof Directional");
    }

    /**
     * Gets the BlockFace of the existing to which the new Block was placed against
     * @param existing
     * @param newBlock
     * @return
     */
    public static BlockFace getPlacedAgainstFace(Block existing, Block newBlock) {
        for(BlockFace blockFace : BlockFace.values()) {
            if(existing.getRelative(blockFace).equals(newBlock)) return blockFace;
        }
        throw new IllegalArgumentException("No BlockFace found");
    }
}