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

import com.jeff_media.jefflib.EntityUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class RespawnPlayer implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        EntityUtils.respawnPlayer(player);
    }

    @Override
    public boolean isRunnableFromConsole() {
        return false;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Did you get respawned?";
    }
}
