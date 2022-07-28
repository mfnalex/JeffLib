package com.jeff_media.jefflib.internal.nms.v1_16_R1.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.GoalFlag;
import com.jeff_media.jefflib.ai.PathNavigation;
import net.minecraft.server.v1_16_R1.EntityCreature;
import net.minecraft.server.v1_16_R1.PathfinderGoal;

import javax.annotation.Nonnull;
import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends PathfinderGoal implements com.jeff_media.jefflib.ai.CustomGoalExecutor {

    private final CustomGoal goal;
    private final EntityCreature pmob;

    public CustomGoalExecutor(CustomGoal goal, EntityCreature pmob) {
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


    @Override
    public void setGoalFlags(EnumSet<GoalFlag> flags) {
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

    private EnumSet<Type> translateGoalFlags(EnumSet<GoalFlag> flags) {
        if(flags == null) return EnumSet.noneOf(Type.class);
        return flags.stream().map(flag -> Type.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Type.class)));
    }

    private EnumSet<GoalFlag> translateFlags(EnumSet<Type> flags) {
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }
}
