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

import com.jeff_media.jefflib.ClassUtils;
import com.jeff_media.jefflib.CollectionUtils;
import com.jeff_media.jefflib.UnitTest;
import com.jeff_media.jefflib.exceptions.UtilityClassInstantiationException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestClassUtils extends UnitTest {

    @Test
    public void testExists() {
        Assertions.assertTrue(ClassUtils.exists("org.bukkit.Bukkit"));
        Assertions.assertFalse(ClassUtils.exists("asdasdafawfawf.awfagwfkawgf.awdwafawf"));
    }

    @Test
    public void testGetCurrentClass() {
        Assertions.assertEquals(this.getClass(), ClassUtils.getCurrentClass(0));
        Assertions.assertEquals(this.getClass(), ClassUtils.getCurrentClass());
    }

    @Test
    public void testCurrentLine() {
        Assertions.assertEquals(45, ClassUtils.getCurrentLineNumber());
    }

    @Test
    public void testCurrentMethod() {
        Assertions.assertEquals("testCurrentMethod", ClassUtils.getCurrentMethodName());
    }

    @Test
    public void testUtilityClass() throws ReflectiveOperationException{
            Class<CollectionUtils> clazz = CollectionUtils.class;
            Constructor<CollectionUtils> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            Assertions.assertThrows(InvocationTargetException.class, constructor::newInstance);
            Exception ex = null;
            try {
                constructor.newInstance();
            } catch (InvocationTargetException ex2) {
                ex = (Exception) ex2.getCause();
            }
            Assertions.assertTrue(ex instanceof UtilityClassInstantiationException);
    }

}
