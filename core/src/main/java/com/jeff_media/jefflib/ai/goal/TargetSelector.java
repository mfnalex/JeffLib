package com.jeff_media.jefflib.ai.goal;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

/**
 * Represents an entity's target selector
 */
public class TargetSelector extends GoalSelector{

    private TargetSelector(@NotNull final Mob mob) {
        super(mob);
    }

    /**
     * Gets this mob's target selector
     */
    public static TargetSelector of(@Nonnull final Mob mob) {
        return new TargetSelector(mob);
    }

    /**
     * Adds a {@link PathfinderGoal} to this mob's target selector
     * @param goal Goal to add
     * @param priority Priority of the goal
     */
    @NMS
    @Override
    public void addGoal(@NotNull final PathfinderGoal goal, final int priority) {
        JeffLib.getNMSHandler().addTargetGoal(mob, goal, priority);
    }

    /**
     * Removes a {@link PathfinderGoal} from this mob's target selector
     * @param goal Goal to remove
     */
    @NMS
    @Override
    public void removeGoal(@NotNull final PathfinderGoal goal) {
        JeffLib.getNMSHandler().removeTargetGoal(mob, goal);
    }

    /**
     * Removes all {@link PathfinderGoal}s from this mob's target selector
     */
    @NMS
    @Override
    public void removeAllGoals() {
        JeffLib.getNMSHandler().removeAllTargetGoals(mob);
    }
}
