package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.entity.LivingEntity;


public class HatchedTemptGoal extends TemptGoal implements com.jeff_media.jefflib.ai.TemptGoal {

    private final LivingEntity bukkitEntity;

    public HatchedTemptGoal(LivingEntity entity, PathfinderMob entitycreature, Ingredient recipeitemstack, double speed, boolean canScare) {
        super(entitycreature, speed, recipeitemstack, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }


}
