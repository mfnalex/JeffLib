package com.jeff_media.jefflib.internal.nms.v1_16_R3.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.server.v1_16_R3.EntityCreature;
import net.minecraft.server.v1_16_R3.PathfinderGoalTempt;
import net.minecraft.server.v1_16_R3.RecipeItemStack;
import org.bukkit.entity.LivingEntity;


public class HatchedTemptGoal extends PathfinderGoalTempt implements PathfinderGoal {

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
