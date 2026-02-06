/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.jeff_media.jefflib.ai.goal;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import com.jeff_media.jefflib.internal.nms.AbstractNMSHandler;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity's target selector
 */

public final class TargetSelector extends GoalSelector {

    private TargetSelector(@NotNull final Mob mob) {
        super(mob);
    }

    /**
     * Gets this mob's target selector
     */
    public static TargetSelector of(@NotNull final Mob mob) {
        return new TargetSelector(mob);
    }

    /**
     * Adds a {@link PathfinderGoal} to this mob's target selector
     *
     * @param goal     Goal to add
     * @param priority Priority of the goal
     */
    @NMS
    @Override
    public void addGoal(@NotNull final PathfinderGoal goal, final int priority) {
        JeffLib.getNMSHandler().addTargetGoal(mob, goal, priority);
    }

    /**
     * Removes a {@link PathfinderGoal} from this mob's target selector
     *
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
