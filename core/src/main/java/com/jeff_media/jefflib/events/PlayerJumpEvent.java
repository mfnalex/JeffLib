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

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.ServerUtils;
import com.jeff_media.jefflib.internal.annotations.Internal;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

/**
 * Represents an event called when a player jumps.
 * <p>
 * On Spigot or CraftBukkit, this event is called through the {@link PlayerMoveEvent}.
 * On Paper or its forks, this event is called through Paper's builtin PlayerJumpEvent.
 */
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final @NotNull Location from;
    @Getter
    private final @NotNull Location to;
    private boolean cancelled;

    public PlayerJumpEvent(@NotNull Player who, @NotNull Location from, @NotNull Location to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    /**
     * Registers the listener needed to call this event
     *
     * @return The listener that was registered
     */
    public static Listener registerListener() {
        Plugin plugin = JeffLib.getPlugin();
        Listener listener;
        if (ServerUtils.isRunningPaper()) { // TODO
            Bukkit.getPluginManager().registerEvents(listener = new PaperPlayerJumpEventListener(), plugin);
        } else {
            Bukkit.getPluginManager().registerEvents(listener = new SpigotListener(), plugin);
        }
        return listener;
    }

    @NotNull
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

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    @Internal
    public static class SpigotListener implements Listener {

        @EventHandler
        public void onJump(PlayerStatisticIncrementEvent statsEvent) {
            if (statsEvent.getStatistic() != Statistic.JUMP) return;
            Player player = statsEvent.getPlayer();
            Location from = player.getLocation();
            Location to = player.getLocation().add(player.getVelocity().add(new Vector(0, 0.42, 0)));
            PlayerJumpEvent ownEvent = new PlayerJumpEvent(statsEvent.getPlayer(), from, to);
            Bukkit.getPluginManager().callEvent(ownEvent);
            if (ownEvent.isCancelled()) {
                statsEvent.setCancelled(true);
                player.teleport(from);
            }
        }
    }
}
