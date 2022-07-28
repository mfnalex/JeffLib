package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import com.jeff_media.jefflib.internal.nms.v1_16_R3.NMS;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.EntityLiving;
import net.minecraft.server.v1_16_R3.PathfinderGoalAvoidTarget;

import java.util.function.Predicate;

public  class HatchedAvoidEntityGoal extends PathfinderGoalAvoidTarget<EntityLiving> implements PathfinderGoal {

    private final org.bukkit.entity.Creature bukkitEntity;

    public HatchedAvoidEntityGoal(final org.bukkit.entity.Creature bukkitEntity,
                                  final EntityCreature mob,
                                  final Predicate<org.bukkit.entity.LivingEntity> bukkitPredicate,
                                  final float maxDistance,
                                  final double walkSpeedModifier,
                                  final double sprintSpeedModifier) {
        super(mob, EntityLiving.class, maxDistance, walkSpeedModifier, sprintSpeedModifier, livingEntity -> bukkitPredicate.test(NMS.toBukkit(livingEntity)));
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public boolean canUse() {
        return a();
    }

    @Override
    public org.bukkit.entity.Creature getBukkitEntity() {
        return bukkitEntity;
    }
}
