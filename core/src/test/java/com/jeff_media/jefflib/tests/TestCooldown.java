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
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertTrue(cooldown.hasCooldown(1));
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Assertions.assertFalse(cooldown.hasCooldown(1));
    }
}
