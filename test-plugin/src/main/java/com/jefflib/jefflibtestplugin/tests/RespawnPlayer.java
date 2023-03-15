package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.EntityUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class RespawnPlayer implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        EntityUtils.respawnPlayer(player);
    }

    @Override
    public boolean isRunnableFromConsole() {
        return false;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Did you get respawned?";
    }
}
