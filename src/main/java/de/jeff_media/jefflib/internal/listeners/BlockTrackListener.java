package de.jeff_media.jefflib.internal.listeners;

import de.jeff_media.jefflib.BlockTracker;
import de.jeff_media.jefflib.JeffLib;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.plugin.Plugin;

/**
 * Keeps track of player placed blocks
 */
public class BlockTrackListener implements Listener {

    private final Plugin plugin = JeffLib.getPlugin();

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if(!BlockTracker.isTrackedBlockType(event.getBlock().getType())) return;
        BlockTracker.setPlayerPlacedBlock(event.getBlock(), true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        if(BlockTracker.isPlayerPlacedBlock(event.getBlock())) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> BlockTracker.setPlayerPlacedBlock(event.getBlock(), false), 1L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onStructureGrow(StructureGrowEvent event) {
        for(BlockState blockState : event.getBlocks()) {
            Block block = blockState.getBlock();
            BlockTracker.setPlayerPlacedBlock(block, false);
        }
    }


}
