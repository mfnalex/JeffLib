package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.ReflUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestReflUtils {

    @Test
    public void testGetClassCached() {
        Assertions.assertFalse(ReflUtils.isClassCached("java.util.List"));
        ReflUtils.getClass("java.util.List");
        Assertions.assertTrue(ReflUtils.isClassCached("java.util.List"));
    }
}
