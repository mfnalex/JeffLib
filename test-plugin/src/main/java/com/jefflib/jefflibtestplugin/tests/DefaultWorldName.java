package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.WorldUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

public class DefaultWorldName implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        runner.print(WorldUtils.getDefaultWorldName());
    }
}
