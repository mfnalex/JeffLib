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

package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.ExpUtils;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExpUtils extends UnitTest {

    @Test
    public void testGetTotalXpRequiredForNextLevel() {
        final Player player = getServer().addPlayer();
        final int requiredForLevel1 = ExpUtils.getXPRequiredForNextLevel(0);
        player.giveExp(requiredForLevel1);
        Assertions.assertEquals(1, player.getLevel());
        Assertions.assertEquals(0, player.getExp());

        player.setLevel(100);
        final int requiredForLevel101 = ExpUtils.getXPRequiredForNextLevel(100);
        player.giveExp(requiredForLevel101 - 1);
        Assertions.assertEquals(100, player.getLevel());
        player.giveExp(1);
        Assertions.assertEquals(101, player.getLevel());
        Assertions.assertEquals(0, player.getExp());
    }
}
