package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_19_R1.util.CraftMagicNumbers;
import org.bukkit.entity.LivingEntity;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class HatchedMoveToBlockGoal extends MoveToBlockGoal implements PathfinderGoal {

    protected final LivingEntity bukkitEntity;

    public HatchedMoveToBlockGoal(LivingEntity bukkitEntity, PathfinderMob pmob, double speed, int searchRange, int verticalSearchRange) {
        super(pmob, speed, searchRange, verticalSearchRange);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    public static class ByMaterialSet extends HatchedMoveToBlockGoal {

        private final Set<Block> validBlockTypes;

        public ByMaterialSet(LivingEntity bukkitEntity, PathfinderMob pMob, double speed, int searchRange, int verticalSearchRange, Set<Material> validBlockTypes) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.validBlockTypes = getBlockTypes(validBlockTypes);
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            return validBlockTypes.contains(levelReader.getBlockState(blockPos).getBlock());
        }

        private static Set<net.minecraft.world.level.block.Block> getBlockTypes(Set<Material> validMaterials) {
            return validMaterials.stream().map(CraftMagicNumbers::getBlock).collect(Collectors.toSet());
        }
    }

    public static class ByBlockPredicate extends HatchedMoveToBlockGoal {

        private final Predicate<org.bukkit.block.Block> predicate;

        public ByBlockPredicate(LivingEntity bukkitEntity, PathfinderMob pMob, double speed, int searchRange, int verticalSearchRange, Predicate<org.bukkit.block.Block> predicate) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.predicate = predicate;
        }

        @Override
        protected boolean isValidTarget(LevelReader levelReader, BlockPos blockPos) {
            return predicate.test(bukkitEntity.getWorld().getBlockAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }
    }

}