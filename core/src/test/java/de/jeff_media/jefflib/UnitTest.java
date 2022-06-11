package de.jeff_media.jefflib;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.MockPlugin;
import be.seeseemelk.mockbukkit.ServerMock;
import lombok.Getter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public class UnitTest {

    @Getter ServerMock server;
    MockPlugin plugin;

    @BeforeEach
    void setup() throws IllegalAccessException {
        server = MockBukkit.mock();
        plugin = MockBukkit.loadSimple(MockPlugin.class);
        JeffLib.setPluginMock(plugin);
    }

    @AfterEach
    void destroy() {
        MockBukkit.unmock();
    }

}
