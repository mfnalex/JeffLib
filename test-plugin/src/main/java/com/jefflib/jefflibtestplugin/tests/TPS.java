package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.ServerUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

public class TPS implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        com.jeff_media.jefflib.data.TPS tps = ServerUtils.getTps();
    }
}
