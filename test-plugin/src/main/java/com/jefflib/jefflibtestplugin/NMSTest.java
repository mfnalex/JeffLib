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

package com.jefflib.jefflibtestplugin;

import org.bukkit.entity.Player;

import javax.annotation.Nullable;

/**
 * Represents a test that can be run by {@link TestRunner}
 */
public interface NMSTest extends Comparable<NMSTest> {

    void run(TestRunner runner, Player player) throws Throwable;

    @Nullable
    default String getConfirmation() {
        return null;
    }

    /**
     * Friendly name of the test, should include the class and method name when possible
     * @return Friendly name of the test
     */
    default String getName() {
        return getClass().getSimpleName();
    }

    default void cleanup() {

    }

    /**
     * Whether this test can run from the console without any player object
     * @return Whether this test can run from the console
     */
    default boolean isRunnableFromConsole() {
        return true;
    }

    /**
     * Whether this test provides a player-related confirmation prompt. This is intended to check whether the test was successful,
     * even if it didn't throw any exceptions.
     * @return Whether this test provides a confirmation prompt
     */
    default boolean hasConfirmation() {
        return false;
    }

    @Override
    default int compareTo(NMSTest o) {
        return -Boolean.compare(hasConfirmation(), o.hasConfirmation());
    }

    default boolean isDone() {
        return true;
    }
}
