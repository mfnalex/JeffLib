package com.jeff_media.jefflib.internal.nms.v1_18_R1.ai;

import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_18_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Creature;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class HatchedMoveToBlockGoal extends MoveToBlockGoal implements PathfinderGoal {

    protected final Creature bukkitEntity;

    public HatchedMoveToBlockGoal(final Creature bukkitEntity, final PathfinderMob pmob, final double speed, final int searchRange, final int verticalSearchRange) {
        super(pmob, speed, searchRange, verticalSearchRange);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

    public static class ByMaterialSet extends HatchedMoveToBlockGoal {

        private final Set<Block> validBlockTypes;

        public ByMaterialSet(final Creature bukkitEntity, final PathfinderMob pMob, final double speed, final int searchRange, final int verticalSearchRange, final Set<Material> validBlockTypes) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.validBlockTypes = getBlockTypes(validBlockTypes);
        }

        private static Set<Block> getBlockTypes(final Set<Material> validMaterials) {
            return validMaterials.stream().map(CraftMagicNumbers::getBlock).collect(Collectors.toSet());
        }

        @Override
        protected boolean isValidTarget(final LevelReader levelReader, final BlockPos blockPos) {
            return validBlockTypes.contains(levelReader.getBlockState(blockPos).getBlock());
        }
    }

    public static class ByBlockPredicate extends HatchedMoveToBlockGoal {

        private final Predicate<org.bukkit.block.Block> predicate;

        public ByBlockPredicate(final Creature bukkitEntity, final PathfinderMob pMob, final double speed, final int searchRange, final int verticalSearchRange, final Predicate<org.bukkit.block.Block> predicate) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.predicate = predicate;
        }

        @Override
        protected boolean isValidTarget(final LevelReader levelReader, final BlockPos blockPos) {
            return predicate.test(bukkitEntity.getWorld().getBlockAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }
    }

}