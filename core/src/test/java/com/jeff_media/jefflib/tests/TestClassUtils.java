package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.ClassUtils;
import com.jeff_media.jefflib.UnitTest;
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
}
