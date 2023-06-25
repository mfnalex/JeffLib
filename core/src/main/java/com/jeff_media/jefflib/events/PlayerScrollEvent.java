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

import com.allatori.annotations.DoNotRename;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Gets called when a player scrolls through their hotbar. When cancelled, the main hand slot will not be changed.
 */
public final class PlayerScrollEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    @Getter
    private final Player who;
    @Getter
    private final ScrollDirection direction;

    private boolean cancelled;

    public PlayerScrollEvent(@NotNull final Player who, final ScrollDirection direction) {
        this.who = who;
        this.direction = direction;
    }

    @DoNotRename
    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @DoNotRename
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(final boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     * Represents the direction in which the player scrolled
     */
    public enum ScrollDirection {
        /**
         * Represents scrolling up / to the right
         */
        UP,
        /**
         * Represents scrolling down / to the left
         */
        DOWN
    }
}
