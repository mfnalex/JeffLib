package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.PathfinderGoalTempt;
import net.minecraft.server.v1_16_R3.RecipeItemStack;
import org.bukkit.entity.Creature;


public class HatchedTemptGoal extends PathfinderGoalTempt implements PathfinderGoal {

    private final Creature bukkitEntity;

    public HatchedTemptGoal(final Creature entity, final EntityCreature pathfinderMob, final RecipeItemStack ingredient, final double speed, final boolean canScare) {
        super(pathfinderMob, speed, ingredient, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public boolean canUse() {
        return a();
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

}
