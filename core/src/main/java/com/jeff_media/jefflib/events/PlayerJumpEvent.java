/*
 *     Copyright (c) 2022. JEFF Media GbR / mfnalex et al.
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>.
 *
 */

package com.jeff_media.jefflib.events;

import com.jeff_media.jefflib.JeffLib;
import com.jeff_media.jefflib.ServerUtils;
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

import javax.annotation.Nonnull;

/**
 * Represents an event called when a player jumps.
 * <p>
 * On Spigot or CraftBukkit, this event is called through the {@link PlayerMoveEvent}.
 * On Paper or its forks, this event is called through Paper's builtin PlayerJumpEvent.
 */
public class PlayerJumpEvent extends PlayerEvent implements Cancellable {

    private static final HandlerList HANDLERS = new HandlerList();
    @Getter
    private final @Nonnull Location from, to;
    private boolean cancelled;

    public PlayerJumpEvent(@Nonnull Player who, @Nonnull Location from, @Nonnull Location to) {
        super(who);
        this.from = from;
        this.to = to;
    }

    /**
     * Registers the listener needed to call this event
     */
    public static void registerListener() {
        Plugin plugin = JeffLib.getPlugin();
        if (ServerUtils.isRunningPaper()) {
            Bukkit.getPluginManager().registerEvents(new PaperListener(), plugin);
        } else {
            Bukkit.getPluginManager().registerEvents(new SpigotListener(), plugin);
        }
    }

    @Nonnull
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

    @Nonnull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static class PaperListener implements Listener {
        @EventHandler
        public void onJump(com.destroystokyo.paper.event.player.PlayerJumpEvent paperEvent) {
            PlayerJumpEvent ownEvent = new PlayerJumpEvent(paperEvent.getPlayer(), paperEvent.getFrom(), paperEvent.getTo());
            Bukkit.getPluginManager().callEvent(ownEvent);
            if (ownEvent.isCancelled()) {
                paperEvent.setCancelled(true);
            }
        }
    }

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
/*        private final Set<UUID> playersOnGround = Sets.newHashSet();

        @EventHandler
        public void onMove(PlayerMoveEvent spigotEvent) {
            final Player player = spigotEvent.getPlayer();
            if (player.getVelocity().getY() > 0) {
                double jumpVelocity = 0.42D;
                if (player.hasPotionEffect(PotionEffectType.JUMP)) {
                    jumpVelocity += (player.getPotionEffect(PotionEffectType.JUMP).getAmplifier() + 1) * 0.1F;
                }
                if (spigotEvent.getPlayer().getLocation().getBlock().getType() != Material.LADDER && playersOnGround.contains(player.getUniqueId())) {
                    if (!player.isOnGround() && NumberUtils.isEqual(player.getVelocity().getY(), jumpVelocity)) {
                        final PlayerJumpEvent ownEvent = new PlayerJumpEvent(player, spigotEvent.getFrom(), spigotEvent.getTo());
                        Bukkit.getPluginManager().callEvent(ownEvent);
                        if(ownEvent.isCancelled()) {
                            spigotEvent.setCancelled(true);
                        }
                    }
                }
            }
            if (player.isOnGround()) {
                playersOnGround.add(player.getUniqueId());
            } else {
                playersOnGround.remove(player.getUniqueId());
            }
        }*/
    }


}
