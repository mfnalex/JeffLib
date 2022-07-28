package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.JeffLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * An extendable {@link PathfinderGoal} that can be used to create custom AI goals.
 */
public abstract class CustomGoal implements PathfinderGoal, CustomGoalExecutor {

    private final LivingEntity bukkitEntity;
    private final CustomGoalExecutor executor;

    protected CustomGoal(final LivingEntity bukkitEntity) {
        this.bukkitEntity = bukkitEntity;
        this.executor = JeffLib.getNMSHandler().getCustomGoalExecutor(this, bukkitEntity);
    }

    /**
     * Returns the {@link CustomGoalExecutor} associated with this goal
     * @return
     */
    public CustomGoalExecutor getExecutor() {
        return executor;
    }

    @Override
    public abstract boolean canUse();

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    @Nonnull
    @Override
    public PathNavigation getNavigation() {
        return executor.getNavigation();
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
