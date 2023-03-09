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

package com.jeff_media.jefflib.ai.navigation;

import com.allatori.annotations.DoNotRename;
import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.internal.annotations.NMS;
import javax.annotation.Nonnull;
import org.bukkit.entity.Mob;

/**
 * Represents an entity's navigation, movement, jump and look controls
 */
@DoNotRename
public final class Controls {

    private final MoveController moveController;
    private final JumpController jumpController;
    private final LookController lookController;
    private final PathNavigation navigation;

    private Controls(@Nonnull final Mob mob) {
        this.moveController = EntityUtils.getMoveController(mob);
        this.jumpController = EntityUtils.getJumpController(mob);
        this.lookController = EntityUtils.getLookController(mob);
        this.navigation = EntityUtils.getNavigation(mob);
    }

    private Controls(final MoveController moveController, final JumpController jumpController, final LookController lookController, final PathNavigation navigation) {
        this.moveController = moveController;
        this.jumpController = jumpController;
        this.lookController = lookController;
        this.navigation = navigation;
    }

    /**
     * Gets this mob's controls
     */
    @NMS
    @Nonnull
    public static Controls of(@Nonnull final Mob mob) {
        return new Controls(mob);
    }

    public static Controls of(@Nonnull final MoveController moveController, @Nonnull final JumpController jumpController, @Nonnull final LookController lookController, @Nonnull final PathNavigation navigation) {
        return new Controls(moveController, jumpController, lookController, navigation);
    }

    /**
     * Gets this mob's {@link MoveController}
     */
    public MoveController getMoveController() {
        return moveController;
    }

    /**
     * Gets this mob's {@link LookController}
     */
    public LookController getLookController() {
        return lookController;
    }

    /**
     * Gets this mob's {@link JumpController}
     */
    public JumpController getJumpController() {
        return jumpController;
    }

    /**
     * Gets this mob's {@link PathNavigation}
     */
    public PathNavigation getNavigation() {
        return navigation;
    }

}
