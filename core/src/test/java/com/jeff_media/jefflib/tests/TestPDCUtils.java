package com.jeff_media.jefflib.tests;

import be.seeseemelk.mockbukkit.MockBukkit;
import com.jeff_media.jefflib.PDCUtils;
import com.jeff_media.jefflib.UnitTest;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestPDCUtils extends UnitTest {

    @Test
    public void testPDC() {
        Player player = getServer().addPlayer("test");
        Player player2 = getServer().addPlayer("test2");
        NamespacedKey key = new NamespacedKey(getPlugin(), "test");
        player.getPersistentDataContainer().set(key, PersistentDataType.STRING,"test");
        Assertions.assertEquals(PersistentDataType.STRING, PDCUtils.getDataType(player.getPersistentDataContainer(),key));
        PDCUtils.copy(player.getPersistentDataContainer(),player2.getPersistentDataContainer());
        Assertions.assertEquals("test",PDCUtils.get(player2, key, PersistentDataType.STRING));
    }
}
