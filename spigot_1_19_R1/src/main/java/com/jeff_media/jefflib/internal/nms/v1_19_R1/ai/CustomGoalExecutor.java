package com.jeff_media.jefflib.internal.nms.v1_19_R1.ai;

import com.jeff_media.jefflib.ai.CustomGoal;
import com.jeff_media.jefflib.ai.GoalFlag;
import com.jeff_media.jefflib.internal.nms.v1_19_R1.NMS;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.Goal;
import org.bukkit.entity.Entity;

import java.util.EnumSet;
import java.util.stream.Collectors;

public class CustomGoalExecutor extends Goal implements com.jeff_media.jefflib.ai.CustomGoalExecutor {

    private final CustomGoal goal;
    private final PathfinderMob pmob;

    public CustomGoalExecutor(CustomGoal goal, PathfinderMob pmob) {
        this.pmob = pmob;
        this.goal = goal;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP, Flag.TARGET));
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
    public void setGoalFlags(EnumSet<GoalFlag> flags) {
        this.setFlags(translateGoalFlags(flags));
    }

    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return translateFlags(this.getFlags());
    }

    @Override
    public boolean moveTo(double x, double y, double z, double speedModifier) {
        return pmob.getNavigation().moveTo(x,y,z,speedModifier);
    }

    @Override
    public boolean moveTo(Entity entity, double speedModifier) {
        return pmob.getNavigation().moveTo(NMS.toNms(entity),speedModifier);
    }

    private EnumSet<Flag> translateGoalFlags(EnumSet<GoalFlag> flags) {
        return flags.stream().map(flag -> Flag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(Flag.class)));
    }

    private EnumSet<GoalFlag> translateFlags(EnumSet<Flag> flags) {
        return flags.stream().map(flag -> GoalFlag.valueOf(flag.name())).collect(Collectors.toCollection(() -> EnumSet.noneOf(GoalFlag.class)));
    }
}
