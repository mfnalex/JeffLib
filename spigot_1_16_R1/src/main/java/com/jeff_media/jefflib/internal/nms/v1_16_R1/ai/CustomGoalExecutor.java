package com.jeff_media.jefflib.internal.nms.v1_16_R1.ai;

import com.jeff_media.jefflib.ai.goal.CustomGoal;
import com.jeff_media.jefflib.ai.goal.GoalFlag;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import net.minecraft.server.v1_16_R1.EntityInsentient;
import net.minecraft.server.v1_16_R1.PathfinderGoal;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends PathfinderGoal implements com.jeff_media.jefflib.ai.goal.CustomGoalExecutor {

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

    @Nonnull
    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return translateFlags(this.i());
    }

    @Override
    public void setGoalFlags(final EnumSet<GoalFlag> flags) {
        this.a(translateGoalFlags(flags));
    }

    @Nonnull
    @Override
    public PathNavigation getNavigation() {
        return new HatchedPathNavigation(pmob.getNavigation());
    }

    @Nonnull
    @Override
    public MoveController getMoveController() {
        return new HatchedMoveController(pmob.getControllerMove());
    }

    @Nonnull
    @Override
    public LookController getLookController() {
        return new HatchedLookController(pmob.getControllerLook());
    }

    @Nonnull
    @Override
    public JumpController getJumpController() {
        return new HatchedJumpController(pmob.getControllerJump());
    }

    @Nonnull
    private static EnumSet<Type> translateGoalFlags(@Nullable final EnumSet<GoalFlag> flags) {
        if (flags == null) return EnumSet.noneOf(Type.class);
        return flags.stream().map(flag -> Type.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Type.class)));
    }

    @Nonnull
    private static EnumSet<GoalFlag> translateFlags(@Nullable final EnumSet<Type> flags) {
        if (flags == null) return EnumSet.noneOf(GoalFlag.class);
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }

    @Override
    public boolean D_() {
        return goal.isInterruptable();
    }
}
