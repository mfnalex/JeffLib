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

import com.jeff_media.jefflib.data.Cooldown;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.concurrent.TimeUnit;

public class TestCooldown {

    @Test
    public void testCooldown() {
        Cooldown cooldown = new Cooldown(TimeUnit.MILLISECONDS);
        Assertions.assertFalse(cooldown.hasCooldown(1));
        cooldown.setCooldown(1, 20, TimeUnit.MILLISECONDS);
        Assertions.assertTrue(cooldown.hasCooldown(1));
        Assertions.assertTrue(cooldown.hasCooldown(1));
        try {
            Thread.sleep(21);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(cooldown.hasCooldown(1));
    }
}
