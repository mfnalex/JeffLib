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

import be.seeseemelk.mockbukkit.UnimplementedOperationException;
import com.jeff_media.jefflib.EntityUtils;
import com.jeff_media.jefflib.UnitTest;
import com.jeff_media.jefflib.exceptions.NMSNotSupportedException;
import org.bukkit.entity.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTranslationKeys extends UnitTest {

    @Test
    public void testEntityTypeKeys() {
        try {
            // Not implemented in MockBukkit
            for (EntityType type : EntityType.values()) {
                String translationKey = EntityUtils.getTranslationKey(type);
                Assertions.assertNotNull(translationKey, "Translation key for " + type + " is null");
            }
        } catch (UnimplementedOperationException ignored) {
            // Ignore
        } catch (NMSNotSupportedException ignored) {
            // Ignore
        }
    }
}
