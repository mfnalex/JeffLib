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

package com.jeff_media.jefflib;

import java.util.Objects;
import lombok.experimental.UtilityClass;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.entity.Player;

/**
 * Experience related methods
 */
@UtilityClass
public class ExpUtils {

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
     * Gets the amount of XP required to reach the next level from the current level and progress
     *
     * @param player Player to check for
     * @return Amount of XP required to reach the next level from the current level and progress
     */
    public static int getXpLeftUntilNextLevel(Player player) {
        int currentLevel = player.getLevel();
        float currentLevelProgress = player.getExp();
        return getXpLeftUntilNextLevel(currentLevel, currentLevelProgress);
    }

    /**
     * Gets the amount of XP required to reach the next level from the current level and progress
     *
     * @param currentLevel         The current level
     * @param currentLevelProgress The current level progress, must be between 0 and 1
     * @return Amount of XP required to reach the next level from the current level and progress
     * @throws IllegalArgumentException If currentLevelProgress is not between 0 and 1
     */
    public static int getXpLeftUntilNextLevel(int currentLevel, float currentLevelProgress) {
        if (currentLevelProgress - NumberUtils.EPSILON_F > 1 || currentLevelProgress + NumberUtils.EPSILON_F < 0) {
            throw new IllegalArgumentException("currentLevelProgress must be between 0 and 1, but was " + currentLevelProgress);
        }
        int xpRequiredFromCurrentLevelToNextLevel = com.jeff_media.jefflib.ExpUtils.getXPRequiredForNextLevel(currentLevel);
        int xpTheyAlreadyHaveInThisLevel = (int) (currentLevelProgress * xpRequiredFromCurrentLevelToNextLevel);
        return xpRequiredFromCurrentLevelToNextLevel - xpTheyAlreadyHaveInThisLevel;
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
