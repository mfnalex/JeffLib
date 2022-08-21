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

package com.jeff_media.jefflib.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.jeff_media.jefflib.PDCUtils;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPDCUtils extends UnitTest {

    @Test
    public void testPDC() {
        Player player = getServer().addPlayer("test");
        Player player2 = getServer().addPlayer("test2");
        NamespacedKey key = new NamespacedKey(getPlugin(), "test");
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING,"test");
        Assertions.assertEquals(PersistentDataType.STRING, PDCUtils.getDataType(player.getPersistentDataContainer(),key));
        PDCUtils.copy(player.getPersistentDataContainer(),player2.getPersistentDataContainer());
        Assertions.assertEquals("test",PDCUtils.get(player2, key, PersistentDataType.STRING));
    }
}
