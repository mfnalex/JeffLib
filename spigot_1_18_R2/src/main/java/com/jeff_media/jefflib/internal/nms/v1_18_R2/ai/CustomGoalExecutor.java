package com.jeff_media.jefflib.internal.nms.v1_18_R2.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.GoalFlag;
import com.jeff_media.jefflib.ai.MoveController;
import com.jeff_media.jefflib.ai.PathNavigation;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends Goal implements com.jeff_media.jefflib.ai.CustomGoalExecutor {

    private final CustomGoal goal;
    private final Mob pmob;

    public CustomGoalExecutor(final CustomGoal goal, final Mob pmob) {
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
    public boolean requiresUpdateEveryTick() {
        return goal.requiresUpdateEveryTick();
    }

    @Override
    public void tick() {
        goal.tick();
    }


    @Override
    public void setGoalFlags(final EnumSet<GoalFlag> flags) {
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

    @Nonnull
    @Override
    public MoveController getMoveControl() {
        return new HatchedMoveController(pmob.getMoveControl());
    }

    @Nonnull private static EnumSet<Flag> translateGoalFlags(@Nullable final EnumSet<GoalFlag> flags) {
        if(flags == null) return EnumSet.noneOf(Flag.class);
        return flags.stream().map(flag -> Flag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Flag.class)));
    }

    @Nonnull private static EnumSet<GoalFlag> translateFlags(@Nullable final EnumSet<Flag> flags) {
        if(flags == null) return EnumSet.noneOf(GoalFlag.class);
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }
}
