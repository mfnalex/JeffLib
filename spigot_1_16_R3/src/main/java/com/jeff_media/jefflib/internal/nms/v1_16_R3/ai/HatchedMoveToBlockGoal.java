package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.LivingEntity;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class HatchedMoveToBlockGoal extends PathfinderGoalGotoTarget implements PathfinderGoal {

    protected final LivingEntity bukkitEntity;

    public HatchedMoveToBlockGoal(LivingEntity bukkitEntity, EntityCreature pmob, double speed, int searchRange, int verticalSearchRange) {
        super(pmob, speed, searchRange, verticalSearchRange);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    public static class ByMaterialSet extends HatchedMoveToBlockGoal {

        private final Set<Block> validBlockTypes;

        public ByMaterialSet(LivingEntity bukkitEntity, EntityCreature pMob, double speed, int searchRange, int verticalSearchRange, Set<Material> validBlockTypes) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.validBlockTypes = getBlockTypes(validBlockTypes);
        }



        private static Set<Block> getBlockTypes(Set<Material> validMaterials) {
            return validMaterials.stream().map(CraftMagicNumbers::getBlock).collect(Collectors.toSet());
        }

        @Override
        public boolean canUse() {
            return a();
        }

        @Override
        protected boolean a(IWorldReader iWorldReader, BlockPosition blockPosition) {
            return validBlockTypes.contains(iWorldReader.getType(blockPosition).getBlock());
        }
    }

    public static class ByBlockPredicate extends HatchedMoveToBlockGoal {

        private final Predicate<org.bukkit.block.Block> predicate;

        public ByBlockPredicate(LivingEntity bukkitEntity, EntityCreature pMob, double speed, int searchRange, int verticalSearchRange, Predicate<org.bukkit.block.Block> predicate) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.predicate = predicate;
        }

        @Override
        protected boolean a(IWorldReader levelReader, BlockPosition blockPos) {
            return predicate.test(bukkitEntity.getWorld().getBlockAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }

        @Override
        public boolean canUse() {
            return a();
        }
    }

}