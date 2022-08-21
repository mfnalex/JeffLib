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

package com.jeff_media.jefflib;

import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;

import java.util.Objects;

/**
 * Experience related methods
 */
@UtilityClass
public final class ExpUtils {

    /**
     * Gets the total amount of XP required to achieve a certain level when starting from 0 levels
     *
     * @param targetLevel Target level
     * @return Amount of XP required to reach the target level
     */
    public static int getTotalXPRequiredForLevel(final int targetLevel) {
        if (targetLevel <= 16) return squared(targetLevel) + (6 * targetLevel);
        if (targetLevel <= 31) return (int) ((2.5 * squared(targetLevel)) - (40.5 * targetLevel) + 360);
        return (int) ((4.5 * squared(targetLevel)) - (162.5 * targetLevel) + 2220);
    }

    private static int squared(final int a) {
        return a * a;
    }

    /**
     * Gets the total amount of XP required to reach currentLevel+1 from currentLevel
     *
     * @param currentLevel Current level
     * @return Amount of XP required to reach currentLevel+1
     */
    public static int getXPRequiredForNextLevel(final int currentLevel) {
        if (currentLevel <= 15) return (2 * currentLevel) + 7;
        if (currentLevel <= 30) return (5 * currentLevel) - 38;
        return (9 * currentLevel) - 158;
    }

    /**
     * Drops an experience orb at the given location with the given amount of experience
     *
     * @param location Location
     * @param xp       Amount of experience
     */
    public static void dropExp(final Location location, final int xp) {
        final ExperienceOrb orb = (ExperienceOrb) Objects.requireNonNull(location.getWorld()).spawnEntity(location, EntityType.EXPERIENCE_ORB);
        orb.setExperience(xp);
    }
}
