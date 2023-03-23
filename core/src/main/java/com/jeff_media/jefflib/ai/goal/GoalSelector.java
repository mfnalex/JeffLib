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

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.internal.annotations.NMS;
import org.bukkit.entity.Mob;

import org.jetbrains.annotations.NotNull;

/**
 * Represents an entity's goal selector
 */
@DoNotRename
public class GoalSelector {

    protected final Mob mob;

    protected GoalSelector(@NotNull final Mob mob) {
        this.mob = mob;
    }

    /**
     * Gets this mob's goal selector
     */
    public static GoalSelector of(@NotNull final Mob mob) {
        return new GoalSelector(mob);
    }

    /**
     * Adds a {@link PathfinderGoal} to this mob's goal selector
     *
     * @param goal     Goal to add
     * @param priority Priority of the goal
     */
    @NMS
    public void addGoal(@NotNull final PathfinderGoal goal, final int priority) {
        JeffLib.getNMSHandler().addGoal(mob, goal, priority);
    }

    /**
     * Removes a {@link PathfinderGoal} from this mob's goal selector
     *
     * @param goal Goal to remove
     */
    @NMS
    public void removeGoal(@NotNull final PathfinderGoal goal) {
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
