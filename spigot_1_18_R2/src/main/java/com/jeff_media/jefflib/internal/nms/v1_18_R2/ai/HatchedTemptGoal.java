package com.jeff_media.jefflib.internal.nms.v1_18_R2.ai;

import com.jeff_media.jefflib.ai.PathfinderGoal;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.crafting.Ingredient;
import org.bukkit.entity.Creature;


public class HatchedTemptGoal extends TemptGoal implements PathfinderGoal {

    private final Creature bukkitEntity;

    public HatchedTemptGoal(final Creature entity, final PathfinderMob pathfinderMob, final Ingredient ingredient, final double speed, final boolean canScare) {
        super(pathfinderMob, speed, ingredient, canScare);
        this.bukkitEntity = entity;
    }

    @Override
    public Creature getBukkitEntity() {
        return bukkitEntity;
    }

}
