package com.jeff_media.jefflib.ai.goal;

import com.jeff_media.jefflib.ai.navigation.*;

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

    /**
     * Returns the {@link MoveController} associated with this goal's entity
     */
    @Nonnull
    MoveController getMoveController();

    /**
     * Returns the {@link LookController} associated with this goal's entity
     */
    @Nonnull
    LookController getLookController();

    /**
     * Returns the {@link JumpController} associated with this goal's entity
     */
    @Nonnull
    JumpController getJumpController();

    /**
     * Returns the {@link Controls} associated with this goal's entity
     */
    @Nonnull
    default Controls getControls() {
        return Controls.of(getMoveController(), getJumpController(), getLookController(), getNavigation());
    }

    //@Nonnull CustomGoal getGoal();

}
