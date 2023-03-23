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

import org.jetbrains.annotations.Nullable;

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
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Is it night?";
    }
}
