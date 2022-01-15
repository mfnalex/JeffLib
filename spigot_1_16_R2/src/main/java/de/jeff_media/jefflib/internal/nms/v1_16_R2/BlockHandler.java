package de.jeff_media.jefflib.internal.nms.v1_16_R2;

import de.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import de.jeff_media.jefflib.internal.nms.AbstractNMSBlockHandler;


public class BlockHandler implements AbstractNMSBlockHandler {
    @Override
    public void playComposterParticlesAndSound(final org.bukkit.block.Block block, final boolean success) {
        throw new NMSNotSupportedException();
    }
}
