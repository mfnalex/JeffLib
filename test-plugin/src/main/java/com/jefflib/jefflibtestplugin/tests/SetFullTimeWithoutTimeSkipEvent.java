package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.WorldUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestException;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.TimeSkipEvent;

import javax.annotation.Nullable;

public class SetFullTimeWithoutTimeSkipEvent implements NMSTest, Listener {

    private TestRunner runner;

    int numberOfSkipEvents = 0;
    long originalFullTime;
    World world;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        this.runner = runner;
        this.world = runner.getWorld();
        this.originalFullTime = world.getFullTime();
        world.setFullTime(0);
        if(world.getFullTime() != 0) {
            throw new TestException("Couldn't set full time to 0 using Bukkit");
        }
        WorldUtils.setFullTimeWithoutTimeSkipEvent(world, 15000, true);
        if(world.getFullTime() != 15000) {
            throw new TestException("Couldn't set full time to 15000 using WorldUtils");
        }
        if(numberOfSkipEvents != 1) {
            throw new TestException("Expected 1 TimeSkipEvent, got " + numberOfSkipEvents);
        }
    }

    @EventHandler
    public void onTimeChange(TimeSkipEvent event) {
        numberOfSkipEvents++;
    }

    @Override
    public void cleanup() {
        //runner.print("Restoring time to " + originalFullTime);
        world.setFullTime(originalFullTime);
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
