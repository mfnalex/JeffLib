package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.BiomeUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class GetBiomeNamespacedKey implements NMSTest {

    private String biomeKey;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        Location location = runner.getSpawn();
        if(player != null) {
            location = player.getLocation();
        }
        biomeKey = BiomeUtils.getBiomeNamespacedKey(location).toString();
        runner.print(biomeKey);
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Is your current biome " + biomeKey + "?";
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }
}
