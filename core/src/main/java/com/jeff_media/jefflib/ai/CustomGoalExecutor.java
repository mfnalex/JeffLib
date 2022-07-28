package com.jeff_media.jefflib.ai;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;

/**
 * Represents an NMS pathfinder Goal that's used to execute a {@link CustomGoal}
 */
public interface CustomGoalExecutor {

    /**
     * Gets the goal flags of this goal's executor
     */
    @Nonnull
    EnumSet<GoalFlag> getGoalFlags();

    /**
     * Sets the goal flags for this goal's executor
     */
    void setGoalFlags(@Nullable final EnumSet<GoalFlag> flags);

    /**
     * Returns the {@link PathNavigation} associated with this goal's entity
     */
    @Nonnull
    PathNavigation getNavigation();

    @Nonnull
    MoveController getMoveControl();

    //@Nonnull CustomGoal getGoal();

}
