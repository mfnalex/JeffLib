package com.jeff_media.jefflib.internal.nms.v1_16_R2.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.GoalFlag;
import com.jeff_media.jefflib.ai.MoveController;
import com.jeff_media.jefflib.ai.PathNavigation;
import net.minecraft.server.v1_16_R2.EntityInsentient;
import net.minecraft.server.v1_16_R2.PathfinderGoal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends PathfinderGoal implements com.jeff_media.jefflib.ai.CustomGoalExecutor {

    private final CustomGoal goal;
    private final EntityInsentient pmob;

    public CustomGoalExecutor(final CustomGoal goal, final EntityInsentient pmob) {
        this.pmob = pmob;
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
    public boolean C_() {
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


    @Override
    public void setGoalFlags(final EnumSet<GoalFlag> flags) {
        this.a(translateGoalFlags(flags));
    }

    @Nonnull
    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return translateFlags(this.i());
    }

    @Nonnull
    @Override
    public PathNavigation getNavigation() {
        return new HatchedPathNavigation(pmob.getNavigation());
    }

    @Nonnull private static EnumSet<Type> translateGoalFlags(@Nullable final EnumSet<GoalFlag> flags) {
        if(flags == null) return EnumSet.noneOf(Type.class);
        return flags.stream().map(flag -> Type.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Type.class)));
    }

    @Nonnull private static EnumSet<GoalFlag> translateFlags(@Nullable final EnumSet<Type> flags) {
        if(flags == null) return EnumSet.noneOf(GoalFlag.class);
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }

    @Nonnull
    @Override
    public MoveController getMoveControl() {
        return new HatchedMoveController(pmob.getControllerMove());
    }
}
