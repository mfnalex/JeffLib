package de.jeff_media.jefflib.events.listeners;

import de.jeff_media.jefflib.BlockTracker;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockTrackListener implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlace(BlockPlaceEvent event) {
        if(!BlockTracker.isTrackedBlockType(event.getBlock().getType())) return;
        BlockTracker.setPlayerPlacedBlock(event.getBlock(), true);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onBreak(BlockBreakEvent event) {
        BlockTracker.setPlayerPlacedBlock(event.getBlock(), false);
    }


}
