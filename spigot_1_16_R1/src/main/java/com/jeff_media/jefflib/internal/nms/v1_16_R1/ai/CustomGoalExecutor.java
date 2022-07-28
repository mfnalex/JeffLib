package com.jeff_media.jefflib.internal.nms.v1_16_R1.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import net.minecraft.server.v1_16_R1.PathfinderGoal;

public class CustomGoalExecutor extends PathfinderGoal {

    private final CustomGoal goal;

    public CustomGoalExecutor(CustomGoal goal) {
        this.goal = goal;
    }

    @Override
    public boolean a() {
        return goal.canUse();
    }

    @Override
    public boolean b() {
        return goal.canContinueToUse();
    }

    @Override
    public boolean D_() {
        return goal.isInterruptable();
    }

    @Override
    public void c() {
        goal.start();
    }

    @Override
    public void d() {
        goal.stop();
    }

    @Override
    public void e() {
        goal.tick();
    }

}
