package com.jeff_media.jefflib.internal.nms.v1_16_R2.ai;

import net.minecraft.server.v1_16_R2.EntityCreature;
import net.minecraft.server.v1_16_R2.PathfinderGoalTempt;
import net.minecraft.server.v1_16_R2.RecipeItemStack;
import org.bukkit.entity.LivingEntity;


public class HatchedTemptGoal extends PathfinderGoalTempt implements com.jeff_media.jefflib.ai.TemptGoal {

    private final LivingEntity bukkitEntity;

    public HatchedTemptGoal(LivingEntity entity, EntityCreature entitycreature, RecipeItemStack recipeitemstack, double speed, boolean canScare) {
        super(entitycreature, speed, recipeitemstack, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public boolean canUse() {
        return a();
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }


}
