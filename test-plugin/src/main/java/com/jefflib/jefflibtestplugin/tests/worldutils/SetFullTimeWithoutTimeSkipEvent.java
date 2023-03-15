package com.jefflib.jefflibtestplugin.tests.worldutils;

import com.jeff_media.jefflib.WorldUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class SetFullTimeWithoutTimeSkipEvent implements NMSTest {

    private TestRunner runner;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        this.runner = runner;
        WorldUtils.setFullTimeWithoutTimeSkipEvent(runner.getWorld(), 8000, true);
    }

    @Override
    public void cleanup() {
        WorldUtils.setFullTimeWithoutTimeSkipEvent(runner.getWorld(), 0, true);
    }

    @Override
    public boolean isRunnableFromConsole() {
        return true;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Is it night?";
    }
}
