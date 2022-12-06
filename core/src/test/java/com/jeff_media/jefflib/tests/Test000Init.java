package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.plugin.Plugin;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class Test000Init extends UnitTest {

    @Test
    public void test() {
        Plugin plugin = JeffLib.getPlugin();
        Assertions.assertNotNull(plugin);
        Assertions.assertEquals(super.getPlugin(), plugin);
    }
}
