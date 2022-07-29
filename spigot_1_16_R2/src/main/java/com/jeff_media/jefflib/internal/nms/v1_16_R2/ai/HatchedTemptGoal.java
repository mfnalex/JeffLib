package com.jeff_media.jefflib.internal.nms.v1_16_R2.ai;

import com.jeff_media.jefflib.ai.goal.PathfinderGoal;
import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.PathfinderGoalTempt;
import net.minecraft.server.v1_16_R2.RecipeItemStack;
import org.bukkit.entity.Creature;


public class HatchedTemptGoal extends PathfinderGoalTempt implements PathfinderGoal {

    private final Creature bukkitEntity;

    public HatchedTemptGoal(final Creature entity, final EntityCreature pathfinderMob, final RecipeItemStack ingredient, final double speed, final boolean canScare) {
        super(pathfinderMob, speed, ingredient, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public boolean canUse() {
        return a();
    }

}
