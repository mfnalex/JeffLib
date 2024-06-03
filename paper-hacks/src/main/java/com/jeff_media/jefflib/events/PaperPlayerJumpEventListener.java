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

package com.jeff_media.jefflib.events;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.lang.reflect.Constructor;

public class PaperPlayerJumpEventListener implements Listener {

    private static final Class<?> JUMP_EVENT_CLASS;
    private static final Constructor<?> JUMP_EVENT_CONSTRUCTOR;

    static {
        try {
            JUMP_EVENT_CLASS = Class.forName("com.jeff_media.jefflib.events.PlayerJumpEvent");
            JUMP_EVENT_CONSTRUCTOR = JUMP_EVENT_CLASS.getConstructor(Player.class, Location.class, Location.class);
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException("Could not get PlayerJumpEvent constructor");
        }
    }

    @EventHandler
    public void onJump(com.destroystokyo.paper.event.player.PlayerJumpEvent paperEvent) {
        try {
            Event ownEvent = (Event) JUMP_EVENT_CONSTRUCTOR.newInstance(paperEvent.getPlayer(), paperEvent.getFrom(), paperEvent.getTo());
            Cancellable ownEventCancellable = (Cancellable) ownEvent;
            Bukkit.getPluginManager().callEvent(ownEvent);
            if (ownEventCancellable.isCancelled()) {
                paperEvent.setCancelled(true);
            }
        } catch (ReflectiveOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

}
