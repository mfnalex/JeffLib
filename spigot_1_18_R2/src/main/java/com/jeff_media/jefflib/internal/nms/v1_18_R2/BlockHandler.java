package com.jeff_media.jefflib.internal.nms.v1_18_R2;

import com.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;
import net.minecraft.world.level.block.ComposterBlock;

public class BlockHandler implements AbstractNMSBlockHandler {
    @Override
    public void playComposterParticlesAndSound(final org.bukkit.block.Block block, final boolean success) {
        ComposterBlock.handleFill(NMS.getLevel(block.getWorld()),NMS.getBlockPos(block),success);
    }
}
