package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import net.minecraft.world.entity.ai.goal.Goal;

public class CustomGoalExecutor extends Goal {

    private final CustomGoal goal;

    public CustomGoalExecutor(CustomGoal goal) {
        this.goal = goal;
    }

    @Override
    public boolean canUse() {
        return goal.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return goal.canContinueToUse();
    }

    @Override
    public boolean isInterruptable() {
        return goal.isInterruptable();
    }

    @Override
    public void start() {
        goal.start();
    }

    @Override
    public void stop() {
        goal.stop();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return goal.requiresUpdateEveryTick();
    }

    @Override
    public void tick() {
        goal.tick();
    }

}
