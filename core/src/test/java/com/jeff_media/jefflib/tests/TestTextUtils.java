package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.TextUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestTextUtils {

    @Test
    public void testColor() {
        Assertions.assertEquals("§x§1§2§3§4§5§6Text", TextUtils.color("<#123456>Text"));
    }

    @Test
    public void testPlaceholders() {
        String expected = "My name is Jeff.";
        String placeholder = "My {property} is {value}.";
        Assertions.assertEquals(expected, TextUtils.replaceInString(placeholder, "{property}", "name", "{value}", "Jeff"));
    }
}
