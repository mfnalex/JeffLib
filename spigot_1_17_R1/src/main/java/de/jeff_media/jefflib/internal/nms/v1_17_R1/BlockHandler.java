package de.jeff_media.jefflib.internal.nms.v1_17_R1;

import de.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import net.minecraft.world.level.block.ComposterBlock;

public class BlockHandler implements AbstractNMSBlockHandler {
    @Override
    public void playComposterParticlesAndSound(final org.bukkit.block.Block block, final boolean success) {
        ComposterBlock.handleFill(NMSInternals.getLevel(block.getWorld()),NMSInternals.getBlockPos(block),success);
    }
}

