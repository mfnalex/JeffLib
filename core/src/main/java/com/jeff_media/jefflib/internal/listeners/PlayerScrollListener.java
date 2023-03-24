/*
 * Copyright (c) 2023. JEFF Media GbR / mfnalex et al.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
