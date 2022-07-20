package com.jeff_media.jefflib.tests;

import com.jeff_media.jefflib.UnitTest;
import com.jeff_media.jefflib.ExpUtils;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestExpUtils extends UnitTest {

    @Test
    public void testGetTotalXpRequiredForNextLevel() {
        final Player player = getServer().addPlayer();
        final int requiredForLevel1 = ExpUtils.getXPRequiredForNextLevel(0);
        player.giveExp(requiredForLevel1);
        Assertions.assertEquals(1, player.getLevel());
        Assertions.assertEquals(0, player.getExp());

        player.setLevel(100);
        final int requiredForLevel101 = ExpUtils.getXPRequiredForNextLevel(100);
        player.giveExp(requiredForLevel101 - 1);
        Assertions.assertEquals(100,player.getLevel());
        player.giveExp(1);
        Assertions.assertEquals(101, player.getLevel());
        Assertions.assertEquals(0, player.getExp());
    }
}
