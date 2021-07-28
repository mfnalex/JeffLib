package de.jeff_media.jefflib.internal.listeners;

import de.jeff_media.jefflib.events.PlayerScrollEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;

/**
 * Listens to PlayerItemHeldEvent and calls the {@link de.jeff_media.jefflib.events.PlayerScrollEvent}
 */
public class PlayerScrollListener implements Listener {

    @EventHandler
    public void onScroll(PlayerItemHeldEvent event) {
        Player who = event.getPlayer();
        PlayerScrollEvent.ScrollDirection direction;
        if (event.getPreviousSlot() == 8 && event.getNewSlot() == 0) {
            direction = PlayerScrollEvent.ScrollDirection.UP;
        } else if (event.getPreviousSlot() == 0 && event.getNewSlot() == 8) {
            direction = PlayerScrollEvent.ScrollDirection.DOWN;
        } else if (event.getPreviousSlot() < event.getNewSlot()) {
            direction = PlayerScrollEvent.ScrollDirection.UP;
        } else {
            direction = PlayerScrollEvent.ScrollDirection.DOWN;
        }
        PlayerScrollEvent scrollEvent = new PlayerScrollEvent(who, direction);
        Bukkit.getPluginManager().callEvent(scrollEvent);
        if (scrollEvent.isCancelled()) {
            event.setCancelled(true);
        }
    }

}
