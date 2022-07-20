package com.jeff_media.jefflib.internal.listeners;

import com.jeff_media.jefflib.events.PlayerScrollEvent;
import com.jeff_media.jefflib.internal.annotations.Internal;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * Listens to PlayerItemHeldEvent and calls the {@link PlayerScrollEvent}
 */
@SuppressWarnings("MethodMayBeStatic")
@Internal
public final class PlayerScrollListener implements Listener {

    @EventHandler
    public void onScroll(final PlayerItemHeldEvent event) {
        final Player who = event.getPlayer();
        final PlayerScrollEvent.ScrollDirection direction;
        if (event.getPreviousSlot() == 8 && event.getNewSlot() == 0) {
            direction = PlayerScrollEvent.ScrollDirection.UP;
        } else if (event.getPreviousSlot() == 0 && event.getNewSlot() == 8) {
            direction = PlayerScrollEvent.ScrollDirection.DOWN;
        } else if (event.getPreviousSlot() < event.getNewSlot()) {
            direction = PlayerScrollEvent.ScrollDirection.UP;
        } else {
            direction = PlayerScrollEvent.ScrollDirection.DOWN;
        }
        final PlayerScrollEvent scrollEvent = new PlayerScrollEvent(who, direction);
        Bukkit.getPluginManager().callEvent(scrollEvent);
        if (scrollEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

}
