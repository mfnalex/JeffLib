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
import com.jeff_media.jefflib.ai.navigation.Controls;
import com.jeff_media.jefflib.ai.navigation.JumpController;
import com.jeff_media.jefflib.ai.navigation.LookController;
import com.jeff_media.jefflib.ai.navigation.MoveController;
import com.jeff_media.jefflib.ai.navigation.PathNavigation;
import java.util.EnumSet;
import org.bukkit.entity.Mob;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * An extendable {@link PathfinderGoal} that can be used to create custom AI goals.
 */
@DoNotRename
public abstract class CustomGoal implements PathfinderGoal, CustomGoalExecutor {

    private final Mob bukkitEntity;
    private final CustomGoalExecutor executor;

    protected CustomGoal(final Mob bukkitEntity) {
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

    @NotNull
    @Override
    public PathNavigation getNavigation() {
        return executor.getNavigation();
    }

    @NotNull
    @Override
    public MoveController getMoveController() {
        return executor.getMoveController();
    }

    @NotNull
    @Override
    public LookController getLookController() {
        return executor.getLookController();
    }

    @NotNull
    @Override
    public JumpController getJumpController() {
        return executor.getJumpController();
    }

    @NotNull
    @Override
    public Controls getControls() {
        return CustomGoalExecutor.super.getControls();
    }

    @NotNull
    @Override
    public EnumSet<GoalFlag> getGoalFlags() {
        return executor.getGoalFlags();
    }

    @Override
    public void setGoalFlags(final @Nullable EnumSet<GoalFlag> flags) {
        executor.setGoalFlags(flags);
    }
}
