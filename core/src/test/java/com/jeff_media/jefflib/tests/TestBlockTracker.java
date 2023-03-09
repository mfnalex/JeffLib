//package com.jeff_media.jefflib.tests;
//
//import static org.junit.Assert.assertFalse;
//import com.jeff_media.jefflib.BlockTracker;
//import com.jeff_media.jefflib.JeffLib;
//import com.jeff_media.jefflib.UnitTest;
//import org.bukkit.Material;
//import org.bukkit.World;
//import org.bukkit.block.Block;
//import org.bukkit.entity.Player;
//import org.bukkit.event.block.BlockPlaceEvent;
//import org.bukkit.inventory.EquipmentSlot;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//
//public class TestBlockTracker extends UnitTest {
//
//    private final World world = getServer().addSimpleWorld("world");
//    private final Player player = getServer().addPlayer();
//    @Test
//    public void testBlockTracker() {
//        JeffLib.registerBlockTracker();
//        Block block = world.getBlockAt(1,2,3);
//        Assertions.assertFalse(BlockTracker.isPlayerPlacedBlock(block));
//        block.setType(Material.DIRT);
//        BlockPlaceEvent event = new BlockPlaceEvent(block, block.getState(), block.getRelative(0,1,0), player.getInventory().getItemInMainHand(), player, false, EquipmentSlot.HAND);
//        getServer().getPluginManager().callEvent(event);
//        Assertions.assertFalse(BlockTracker.isPlayerPlacedBlock(block));
//        event = new BlockPlaceEvent(block, block.getState(), block.getRelative(0,1,0), player.getInventory().getItemInMainHand(), player, false, EquipmentSlot.HAND);
//        getServer().getPluginManager().callEvent(event);
//        Assertions.assertTrue(BlockTracker.isPlayerPlacedBlock(block));
//
//    }
//}
