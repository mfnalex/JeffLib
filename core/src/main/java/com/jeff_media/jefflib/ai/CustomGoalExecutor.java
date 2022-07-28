package com.jeff_media.jefflib.ai;

import org.bukkit.entity.Entity;

import java.util.EnumSet;

public interface CustomGoalExecutor {

    void setGoalFlags(EnumSet<GoalFlag> flags);

    EnumSet<GoalFlag> getGoalFlags();

    boolean moveTo(double x, double y, double z, double speedModifier);

    boolean moveTo(Entity entity, double speedModifier);

}
