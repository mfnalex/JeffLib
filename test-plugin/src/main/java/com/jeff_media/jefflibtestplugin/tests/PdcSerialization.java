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

package com.jeff_media.jefflibtestplugin.tests;

import com.jeff_media.jefflib.PDCUtils;
import com.jeff_media.jefflibtestplugin.NMSTest;
import com.jeff_media.jefflibtestplugin.TestException;
import com.jeff_media.jefflibtestplugin.TestRunner;
import java.io.IOException;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Pig;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PdcSerialization implements NMSTest {

    private final NamespacedKey key = NamespacedKey.fromString("jefflibtestplugin:mykey");
    private Entity entity;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        entity = runner.getWorld().spawn(runner.getBlockInFront().getLocation(), Pig.class, pig -> {
            PersistentDataContainer pdc = pig.getPersistentDataContainer();
            pdc.set(key, PersistentDataType.STRING, "abc");
        });
        String json = PDCUtils.serialize(entity.getPersistentDataContainer());
        runner.print(json);
        entity.remove();
        entity = runner.getWorld().spawn(runner.getBlockInFront().getLocation(), Sheep.class, sheep -> {
            try {
                PDCUtils.deserialize(json, sheep.getPersistentDataContainer());
            } catch (IOException e) {
                throw new TestException("Couldn't deserialize PDC", e);
            }
            String result = sheep.getPersistentDataContainer().get(key, PersistentDataType.STRING);
            if (result == null || !result.equals("abc")) {
                throw new TestException("Expected abc, got " + result);
            }
        });
        entity.remove();
    }
}
