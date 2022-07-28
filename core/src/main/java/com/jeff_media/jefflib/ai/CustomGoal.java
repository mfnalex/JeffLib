package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.JeffLib;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * An extendable {@link PathfinderGoal} that can be used to create custom AI goals.
 */
public abstract class CustomGoal implements PathfinderGoal, CustomGoalExecutor {

    private final Mob bukkitEntity;
    private final CustomGoalExecutor executor;

    protected CustomGoal(final Creature bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
        this.executor = JeffLib.getNMSHandler().getCustomGoalExecutor(this, bukkitEntity);
    }

    /**
     * Returns the {@link CustomGoalExecutor} associated with this goal
     *
     * @return
     */
    public CustomGoalExecutor getExecutor() {
        return executor;
    }

    @Override
    public abstract boolean canUse();

    @Override
    public Mob getBukkitEntity() {
        return bukkitEntity;
    }

    @Nonnull
    @Override
    public PathNavigation getNavigation() {
        return executor.getNavigation();
    }

    @Nonnull
    @Override
    public MoveController getMoveControl() {
        return executor.getMoveControl();
    }

    @Override
    public void setGoalFlags(final @Nullable EnumSet<GoalFlag> flags) {
        executor.setGoalFlags(flags);
    }

    @Nonnull
    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return executor.getGoalFlags();
    }
}
