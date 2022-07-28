package com.jeff_media.jefflib.internal.nms.v1_18_R1.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.entity.LivingEntity;


public class HatchedTemptGoal extends TemptGoal implements PathfinderGoal {

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
