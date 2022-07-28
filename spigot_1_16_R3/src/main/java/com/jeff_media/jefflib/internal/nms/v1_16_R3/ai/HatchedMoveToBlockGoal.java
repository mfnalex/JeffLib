package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.server.v1_16_R3.*;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_16_R3.util.CraftMagicNumbers;
import org.bukkit.entity.Creature;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class HatchedMoveToBlockGoal extends PathfinderGoalGotoTarget implements PathfinderGoal {

    protected final Creature bukkitEntity;

    public HatchedMoveToBlockGoal(final Creature bukkitEntity, final EntityCreature pmob, final double speed, final int searchRange, final int verticalSearchRange) {
        super(pmob, speed, searchRange, verticalSearchRange);
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

    public static class ByMaterialSet extends HatchedMoveToBlockGoal {

        private final Set<Block> validBlockTypes;

        public ByMaterialSet(final Creature bukkitEntity, final EntityCreature pMob, final double speed, final int searchRange, final int verticalSearchRange, final Set<Material> validBlockTypes) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.validBlockTypes = getBlockTypes(validBlockTypes);
        }



        private static Set<Block> getBlockTypes(final Set<Material> validMaterials) {
            return validMaterials.stream().map(CraftMagicNumbers::getBlock).collect(Collectors.toSet());
        }

        @Override
        public boolean canUse() {
            return a();
        }

        @Override
        protected boolean a(final IWorldReader iWorldReader, final BlockPosition blockPosition) {
            return validBlockTypes.contains(iWorldReader.getType(blockPosition).getBlock());
        }
    }

    public static class ByBlockPredicate extends HatchedMoveToBlockGoal {

        private final Predicate<org.bukkit.block.Block> predicate;

        public ByBlockPredicate(final Creature bukkitEntity, final EntityCreature pMob, final double speed, final int searchRange, final int verticalSearchRange, final Predicate<org.bukkit.block.Block> predicate) {
            super(bukkitEntity, pMob, speed, searchRange, verticalSearchRange);
            this.predicate = predicate;
        }

        @Override
        protected boolean a(final IWorldReader levelReader, final BlockPosition blockPos) {
            return predicate.test(bukkitEntity.getWorld().getBlockAt(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        }

        @Override
        public boolean canUse() {
            return a();
        }
    }

}