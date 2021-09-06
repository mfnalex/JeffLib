package de.jeff_media.jefflib.events;

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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
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
