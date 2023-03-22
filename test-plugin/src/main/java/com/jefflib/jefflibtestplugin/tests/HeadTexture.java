package com.jefflib.jefflibtestplugin.tests;

import com.jeff_media.jefflib.SkullUtils;
import com.jefflib.jefflibtestplugin.NMSTest;
import com.jefflib.jefflibtestplugin.TestRunner;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class HeadTexture implements NMSTest {

    private Block block;

    @Override
    public void run(TestRunner runner, Player player) throws Throwable {
        block = runner.getBlockInFront();
        block.setType(Material.PLAYER_WALL_HEAD);
        SkullUtils.setHeadTexture(block, "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTkyZTMxZmZiNTljOTBhYjA4ZmM5ZGMxZmUyNjgwMjAzNWEzYTQ3YzQyZmVlNjM0MjNiY2RiNDI2MmVjYjliNiJ9fX0=");
    }

    @Nullable
    @Override
    public String getConfirmation() {
        return "Does the skull have a green checkmark texture?";
    }

    @Override
    public void cleanup() {
        block.setType(Material.AIR);
    }

    @Override
    public boolean hasConfirmation() {
        return true;
    }
}
