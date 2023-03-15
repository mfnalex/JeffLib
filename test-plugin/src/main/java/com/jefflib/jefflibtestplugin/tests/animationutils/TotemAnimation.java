package com.jefflib.jefflibtestplugin.tests.animationutils;

import com.jeff_media.jefflib.AnimationUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

public class TotemAnimation implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        AnimationUtils.playTotemAnimation(player);
    }

    @Override
    public String getConfirmation() {
        return "Did you see a totem animation?";
    }

    @Override
    public boolean isRunnableFromConsole() {
        return false;
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }
}
