package de.jeff_media.jefflib.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

/**
 * Gets called when a player scrolls through their hotbar. When cancelled, the main hand slot will not be changed.
 */
public class PlayerScrollEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();

    private final Player who;
    private final ScrollDirection direction;

    private boolean cancelled = false;

    public PlayerScrollEvent(@NotNull Player who, ScrollDirection direction) {
        this.who = who;
        this.direction = direction;
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public enum ScrollDirection {
        UP, DOWN
    }
}
