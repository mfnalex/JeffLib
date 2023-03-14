package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.ServerUtils;
import com.jeff_media.jefflib.UnitTest;
import junit.framework.TestListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.concurrent.ExecutionException;

public class TestEffectiveMotd extends UnitTest {

    private static final String ORIGINAL_MOTD = "A Minecraft Server";
    private static final String CHANGED_MOTD = String.valueOf(Math.random());

    @Test
    public void testMotdEventWithListener() throws ExecutionException, InterruptedException {
        getServer().getPluginManager().registerEvents(new TestListener(), getPlugin());
        String motd = ServerUtils.getEffectiveMotd().get();
        Assertions.assertEquals(CHANGED_MOTD, motd);
    }

    @Test
    public void testMotdEventWithoutListener() throws ExecutionException, InterruptedException {
        String motd = ServerUtils.getEffectiveMotd().get();
        Assertions.assertEquals(ORIGINAL_MOTD, motd);
    }

    private static class TestListener implements Listener {
        @EventHandler
        public void onServerListPing(ServerListPingEvent event) {
            event.setMotd(CHANGED_MOTD);
        }
    }
}
