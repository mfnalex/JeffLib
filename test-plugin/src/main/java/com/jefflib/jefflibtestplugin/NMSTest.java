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
