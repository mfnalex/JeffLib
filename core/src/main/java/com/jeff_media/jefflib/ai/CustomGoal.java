package com.jeff_media.jefflib.ai;

import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.JeffLib;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import javax.annotation.Nonnull;
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

    public CustomGoalExecutor getExecutor() {
        return executor;
    }

    @Override
    public abstract boolean canUse();

    @Override
    public LivingEntity getBukkitEntity() {
        return bukkitEntity;
    }

    @Override
    public boolean moveTo(final double x, final double y, final double z, final double speed) {
        return executor.moveTo(x,y,z,speed);
    }

    public boolean moveTo(@Nonnull final Location location, final double speed) {
        return executor.moveTo(location.getX(),location.getY(),location.getZ(),speed);
    }

    @Override
    public boolean moveTo(@Nonnull final Entity entity, final double speed) {
        return executor.moveTo(entity,speed);
    }

    @Override
    public void setGoalFlags(EnumSet<GoalFlag> flags) {
        executor.setGoalFlags(flags);
    }

    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return executor.getGoalFlags();
    }
}
