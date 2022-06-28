package de.jeff_media.jefflib.internal.nms.v1_18_R2;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_18_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R2.block.CraftBlock;

public final class NMSInternals {

    public static Level getLevel(final World world) {
        return ((CraftWorld)world).getHandle();
    }

    public static BlockPos getBlockPos(final Block block) {
        return ((CraftBlock)block).getPosition();
    }

}
