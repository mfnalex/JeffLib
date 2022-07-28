package com.jeff_media.jefflib.internal.nms.v1_17_R1.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.GoalFlag;
import com.jeff_media.jefflib.ai.PathNavigation;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends Goal implements com.jeff_media.jefflib.ai.CustomGoalExecutor {

    private final CustomGoal goal;
    private final PathfinderMob pmob;

    public CustomGoalExecutor(CustomGoal goal, PathfinderMob pmob) {
        this.pmob = pmob;
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
    public void tick() {
        goal.tick();
    }


    @Override
    public void setGoalFlags(EnumSet<GoalFlag> flags) {
        this.setFlags(translateGoalFlags(flags));
    }

    @Nonnull
    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return translateFlags(this.getFlags());
    }

    @Nonnull
    @Override
    public PathNavigation getNavigation() {
        return new HatchedPathNavigation(pmob.getNavigation());
    }

    private EnumSet<Flag> translateGoalFlags(EnumSet<GoalFlag> flags) {
        if(flags == null) return EnumSet.noneOf(Flag.class);
        return flags.stream().map(flag -> Flag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Flag.class)));
    }

    private EnumSet<GoalFlag> translateFlags(EnumSet<Flag> flags) {
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }
}
