/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.ai.goal;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.ai.navigation.Controls;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.EnumSet;


/**
 * Represents an NMS pathfinder Goal that's used to execute a {@link CustomGoal}
 */
@DoNotRename
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
