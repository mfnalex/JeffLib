package com.jefflib.jefflibtestplugin.tests;

import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.entity.Player;

public class HelloWorld implements NMSTest {
    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        runner.print("Hello world!");
    }
}
