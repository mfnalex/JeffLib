package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import com.jeff_media.jefflib.internal.nms.v1_19_R1.NMS;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;

import java.util.function.Predicate;

public  class HatchedAvoidEntityGoal extends AvoidEntityGoal<LivingEntity> implements PathfinderGoal {

    private final org.bukkit.entity.Creature bukkitEntity;

    public HatchedAvoidEntityGoal(final org.bukkit.entity.Creature bukkitEntity,
                                  final PathfinderMob mob,
                                  final Predicate<org.bukkit.entity.LivingEntity> bukkitPredicate,
                                  final float maxDistance,
                                  final double walkSpeedModifier,
                                  final double sprintSpeedModifier) {
        super(mob, LivingEntity.class, maxDistance, walkSpeedModifier, sprintSpeedModifier, livingEntity -> bukkitPredicate.test(NMS.toBukkit(livingEntity)));
        this.bukkitEntity = bukkitEntity;
    }

    @Override
    public org.bukkit.entity.Creature getBukkitEntity() {
        return bukkitEntity;
    }
}
