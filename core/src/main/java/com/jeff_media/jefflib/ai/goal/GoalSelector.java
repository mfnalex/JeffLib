package com.jeff_media.jefflib.ai.goal;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.entity.Mob;

import javax.annotation.Nonnull;

/**
 * Represents an entity's goal selector
 */
public class GoalSelector {

    protected final Mob mob;

    protected GoalSelector(@Nonnull final Mob mob) {
        this.mob = mob;
    }

    /**
     * Gets this mob's goal selector
     */
    public static GoalSelector of(@Nonnull final Mob mob) {
        return new GoalSelector(mob);
    }

    /**
     * Adds a {@link PathfinderGoal} to this mob's goal selector
     * @param goal Goal to add
     * @param priority Priority of the goal
     */
    @NMS
    public void addGoal(@Nonnull final PathfinderGoal goal, final int priority) {
        JeffLib.getNMSHandler().addGoal(mob, goal, priority);
    }

    /**
     * Removes a {@link PathfinderGoal} from this mob's goal selector
     * @param goal Goal to remove
     */
    @NMS
    public void removeGoal(@Nonnull final PathfinderGoal goal) {
        JeffLib.getNMSHandler().removeGoal(mob, goal);
    }

    /**
     * Removes all {@link PathfinderGoal}s from this mob's goal selector
     */
    @NMS
    public void removeAllGoals() {
        JeffLib.getNMSHandler().removeAllGoals(mob);
    }

}
