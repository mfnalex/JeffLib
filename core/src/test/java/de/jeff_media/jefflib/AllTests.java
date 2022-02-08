package de.jeff_media.jefflib;

import be.seeseemelk.mockbukkit.MockBukkit;
import be.seeseemelk.mockbukkit.ServerMock;
import be.seeseemelk.mockbukkit.WorldMock;
import be.seeseemelk.mockbukkit.entity.PlayerMock;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AllTests {

    ServerMock server;
    MockPlugin plugin;
    PlayerMock player;
    WorldMock world;

    @BeforeEach
    void setup() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MockPlugin.class);
        player = server.addPlayer();
        world = new WorldMock(Material.STONE,-64,256,64);
    }

    @AfterEach
    void destroy() {
        MockBukkit.unmock();
    }

    @Test
    void testEnumUtils() {
        JeffLib.init(plugin, false);
        assert EnumUtils.getIfPresent(Material.class,"DIRT").orElse(null) == Material.DIRT;
        List<Material> materials = EnumUtils.getEnumsFromList(Material.class, Arrays.asList("DIRT","DIAMOND_PICKAXE","invalid block name","NETHERITE_SHOVEL"), Collectors.toList());
        assert materials.get(0) == Material.DIRT;
        assert materials.get(1) == Material.DIAMOND_PICKAXE;
        assert materials.get(2) == Material.NETHERITE_SHOVEL;
    }

    /*@Test
    void testBlockTracker() {
        JeffLib.init(plugin, false);
        JeffLib.registerBlockTracker();
        BlockTracker.addTrackedBlockType(Material.DIRT);
        Block block1 = world.getBlockAt(0,64,0);
        Block block2 = world.getBlockAt(0,64,1);
        Block block3 = world.getBlockAt(0,64,2);
        player.simulateBlockPlace(Material.DIRT, block1.getLocation());
        System.out.println(block1.getType());
        player.simulateBlockPlace(Material.DIAMOND_BLOCK, block2.getLocation());
        assert BlockTracker.isPlayerPlacedBlock(block1);
        assert !BlockTracker.isPlayerPlacedBlock(block2);
        assert !BlockTracker.isPlayerPlacedBlock(block3);
    }*/


}
