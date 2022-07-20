package com.jeff_media.jefflib.internal.nms.v1_19_R1;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.block.CraftBlock;

public final class NMSInternals {

    public static Level getLevel(final World world) {
        return ((CraftWorld)world).getHandle();
    }

    public static BlockPos getBlockPos(final Block block) {
        return ((CraftBlock)block).getPosition();
    }

}
